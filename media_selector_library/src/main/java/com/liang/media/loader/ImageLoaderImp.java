package com.liang.media.loader;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.liang.media.GlideApp;
import com.liang.media.GlideRequest;
import com.liang.media.R;
import com.liang.media.RequestConfig;

public class ImageLoaderImp implements ImageLoader {
    @Override
    public void loadThumbnailImage(Context context, Uri uri, ImageView imageView) {
        loadImage(context, uri, R.drawable.image_error,
                RequestConfig.getInstance().thumbnailSize, imageView);
    }

    @Override
    public void loadNormalImage(Context context, Uri uri, ImageView imageView) {
        loadImage(context, uri, R.drawable.image_error, 0, imageView);
    }

    @Override
    public void loadAudioImage(Context context, Uri uri, ImageView imageView) {
        GlideApp.with(context)
                .load(R.drawable.audio_def)
                .placeholder(R.drawable.audio_def)
                .override(RequestConfig.getInstance().thumbnailSize,
                        RequestConfig.getInstance().thumbnailSize)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadFolderImage(Context context, Uri uri, ImageView imageView) {
        loadImage(context, uri, R.drawable.audio_folder, 0, imageView);
    }

    @Override
    public void loadImage(Context context, Uri uri, int placeholder, int size, ImageView imageView) {
        GlideRequest<Drawable> glideRequest = GlideApp.with(context).load(uri);
        if (placeholder != 0) {
            glideRequest.placeholder(placeholder);
        }

        if (size > 0) {
            glideRequest.override(size, size);
        }

        glideRequest.centerCrop();
        glideRequest.into(imageView);
    }
}
