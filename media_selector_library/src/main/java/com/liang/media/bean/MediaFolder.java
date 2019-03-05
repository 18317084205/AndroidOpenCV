package com.liang.media.bean;


import android.database.Cursor;
import android.provider.MediaStore;
import com.liang.media.loader.FolderCursorLoader;

import java.util.ArrayList;
import java.util.List;

public class MediaFolder {
    public final String id;
    public final String path;
    public final String name;
    public long count;


    public MediaFolder(Cursor cursor) {
        this(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                cursor.getLong(cursor.getColumnIndex(FolderCursorLoader.COLUMN_COUNT)));
    }

    public MediaFolder(String id, String path, String name, long count) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.count = count;
    }

    public static List<MediaFolder> ofList(Cursor cursor) {
        List<MediaFolder> mediaFolders = new ArrayList<>();
        while (cursor.moveToNext()) {
            mediaFolders.add(new MediaFolder(cursor));
        }
        return mediaFolders;
    }
}
