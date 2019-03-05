package com.liang.media;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

import com.liang.media.listener.MediaRequestCallback;
import com.liang.media.ui.SelectorActivity;

import java.lang.ref.WeakReference;
import java.util.Set;

public class MediaRequest {

    public static final int REQUEST_CODE_PREVIEW = 23;
    public static final int REQUEST_CODE_CAPTURE = 24;

    public static final String EXTRA_RESULT_URI = "extra_result_uri";
    public static final String EXTRA_RESULT_PATH = "extra_result_path";

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private MediaRequestCallback mMediaRequestCallback;

    private RequestConfig mRequestConfig;

    MediaRequest(Activity activity) {
        this(activity, null);
    }

    MediaRequest(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private MediaRequest(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
        mRequestConfig = RequestConfig.getInstance();
        mRequestConfig.init(activity);
        mRequestConfig.mimeTypeSet = MediaType.ofImageAndVideo();
    }

    public MediaRequest mimeType(Set<MediaType> mimeTypes) {
        mRequestConfig.mimeTypeSet = mimeTypes;
        return this;
    }

    public MediaRequest canTakePicture(boolean takePicture) {
        mRequestConfig.canTakePicture = takePicture;
        return this;
    }

    public MediaRequest setCameraIcon(@DrawableRes int cameraIconResId) {
        mRequestConfig.ic_camera = cameraIconResId;
        return this;
    }

    public MediaRequest selectedMaxCount(int selectedMaxCount) {
        mRequestConfig.selectedMaxCount = Math.max(1, selectedMaxCount);
        return this;
    }

    public MediaRequest spanCount(int spanCount) {
        mRequestConfig.spanCount = spanCount;
        return this;
    }

    public MediaRequest requestCode(int requestCode) {
        mRequestConfig.requestCode = requestCode;
        return this;
    }

    public MediaRequest setMediaRequestCallback(MediaRequestCallback mediaRequestCallback) {
        mMediaRequestCallback = mediaRequestCallback;
        return this;
    }

    public MediaRequest startSelector() {
        mContext.get().startActivityForResult(new Intent(mContext.get(), SelectorActivity.class), mRequestConfig.requestCode);
        return this;
    }

    public MediaRequest startCapture() {
        mContext.get().startActivityForResult(new Intent(mContext.get(), SelectorActivity.class), mRequestConfig.requestCode);
        return this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestConfig.requestCode && resultCode == Activity.RESULT_OK
                && mMediaRequestCallback != null && data != null) {
            mMediaRequestCallback.onMediaCallback(
                    data.<Uri>getParcelableArrayListExtra(EXTRA_RESULT_URI),
                    data.getStringArrayListExtra(EXTRA_RESULT_PATH));
        }
    }
}
