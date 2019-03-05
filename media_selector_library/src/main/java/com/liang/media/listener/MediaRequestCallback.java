package com.liang.media.listener;

import android.net.Uri;

import java.util.List;

public interface MediaRequestCallback {
    void onMediaCallback(List<Uri> uris, List<String> paths);
}
