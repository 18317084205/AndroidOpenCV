package com.liang.media;

import android.content.Context;

import com.liang.media.loader.ImageLoader;
import com.liang.media.loader.ImageLoaderImp;

import java.util.HashSet;
import java.util.Set;

public class RequestConfig {

    public Set<MediaType> mimeTypeSet = new HashSet<>();
    public int spanCount;
    public int dividerWidth;
    public int thumbnailSize;
    public Context context;
    public ImageLoader imageLoader;
    public boolean canTakePicture;
    public int ic_camera;
    public int selectedMaxCount;
    public int requestCode;


    private RequestConfig() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static RequestConfig getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public boolean isCheck() {
        return selectedMaxCount > 1;
    }

    private static final class InstanceHolder {
        private static final RequestConfig INSTANCE = new RequestConfig();
    }

    public void init(Context context) {
        mimeTypeSet.clear();
        spanCount = 4;
        dividerWidth = 10;
        initThumbnailSize(context);
        imageLoader = new ImageLoaderImp();
        canTakePicture = true;
        ic_camera = R.drawable.ic_camera;
        selectedMaxCount = 9;
        requestCode = 1980;
    }

    private void initThumbnailSize(Context context) {
        thumbnailSize = MediaUtils.getImageThumbnailSize(context);
    }


    public boolean onlyShowImages() {
        return MediaType.ofImage().containsAll(mimeTypeSet);
    }

    public boolean onlyShowVideos() {
        return MediaType.ofVideo().containsAll(mimeTypeSet);
    }

    public boolean onlyShowImagesAndVideos() {
        return MediaType.ofImageAndVideo().containsAll(mimeTypeSet);
    }

    public boolean onlyShowAudio() {
        return MediaType.ofAudio().containsAll(mimeTypeSet);
    }
}
