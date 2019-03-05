package com.liang.media;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class Media {

    public static MediaRequest with(Activity activity) {
        return new MediaRequest(activity);
    }

    public static MediaRequest with(Fragment fragment) {
        return new MediaRequest(fragment);
    }

}
