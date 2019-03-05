
package com.liang.media.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.liang.media.R;
import com.liang.media.RequestConfig;


public class FolderCursorLoader extends CursorLoader {
    public static final String COLUMN_COUNT = "count";
    public static final String ALL_FOLDER_ID = String.valueOf(-1);
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String BUCKET_ORDER_BY = MediaStore.MediaColumns.DATE_ADDED + " DESC";

    private static final String[] COLUMNS = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DATA,
            COLUMN_COUNT};

    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DATA,
            "COUNT(*) AS " + COLUMN_COUNT};

    private static final String SELECTION_ALL =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";

    private static final String SELECTION =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";

    private static final String[] SELECTION_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO),
    };

    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";

    private static String[] getSelectionArgsForSingleMediaType(int mediaType) {
        return new String[]{String.valueOf(mediaType)};
    }

    private FolderCursorLoader(Context context, String selection, String[] selectionArgs) {
        super(context, QUERY_URI, PROJECTION, selection, selectionArgs, BUCKET_ORDER_BY);
    }

    public static CursorLoader newInstance(Context context) {
        String selection;
        String[] selectionArgs;
        if (RequestConfig.getInstance().onlyShowImages()) {
            selection = SELECTION_FOR_SINGLE_MEDIA_TYPE;
            selectionArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
        } else if (RequestConfig.getInstance().onlyShowVideos()) {
            selection = SELECTION_FOR_SINGLE_MEDIA_TYPE;
            selectionArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
        } else if (RequestConfig.getInstance().onlyShowAudio()) {
            selection = SELECTION_FOR_SINGLE_MEDIA_TYPE;
            selectionArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO);
        } else if (RequestConfig.getInstance().onlyShowImagesAndVideos()) {
            selection = SELECTION;
            selectionArgs = new String[]{SELECTION_ARGS[0], SELECTION_ARGS[1]};
        } else {
            selection = SELECTION_ALL;
            selectionArgs = SELECTION_ARGS;
        }
        return new FolderCursorLoader(context, selection, selectionArgs);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor folders = super.loadInBackground();
        MatrixCursor allFolder = new MatrixCursor(COLUMNS);
        int totalCount = 0;
        String allFolderPath = "";
        if (folders != null) {
            while (folders.moveToNext()) {
                totalCount += folders.getInt(folders.getColumnIndex(COLUMN_COUNT));
            }
            if (folders.moveToFirst()) {
                allFolderPath = folders.getString(folders.getColumnIndex(MediaStore.MediaColumns.DATA));
            }
        }
        allFolder.addRow(new String[]{ALL_FOLDER_ID, ALL_FOLDER_ID, getContext().getString(R.string.folder_all), allFolderPath,
                String.valueOf(totalCount)});

        return new MergeCursor(new Cursor[]{allFolder, folders});
    }

}