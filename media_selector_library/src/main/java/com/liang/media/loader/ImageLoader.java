package com.liang.media.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public interface ImageLoader {
    void loadThumbnailImage(Context context, Uri uri, ImageView imageView);

    void loadNormalImage(Context context, Uri uri, ImageView imageView);

    void loadAudioImage(Context context, Uri uri, ImageView imageView);

    void loadFolderImage(Context context, Uri uri, ImageView imageView);

    void loadImage(Context context, Uri uri, int placeholder, int size, ImageView imageView);
}
