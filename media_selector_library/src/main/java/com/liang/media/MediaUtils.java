package com.liang.media;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MediaUtils {

    public static float getSizeInMB(long sizeInBytes) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern("0.0");
        String result = df.format((float) sizeInBytes / 1024 / 1024);
        result = result.replaceAll(",", ".");
        return Float.valueOf(result);
    }

    public static int getImageThumbnailSize(Context context) {
        int spanCount = RequestConfig.getInstance().spanCount;
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int availableWidth = screenWidth - RequestConfig.getInstance().dividerWidth * (spanCount + 1);
        return (int) (availableWidth / spanCount * 0.5f);
    }

    public static Bitmap getAudioCover(Context context,Uri uri) {
        MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
        myRetriever.setDataSource(context, uri); // the URI of audio file
        byte[] artwork;

        artwork = myRetriever.getEmbeddedPicture();

        if (artwork != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
            return bitmap;
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.audio_def);
        }
    }

    public static String getPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }

        if ("content".equals(uri.getScheme())) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA},
                        null, null, null);
                if (cursor == null || !cursor.moveToFirst()) {
                    return null;
                }
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return uri.getPath();
    }
}
