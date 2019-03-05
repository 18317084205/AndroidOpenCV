package com.liang.media;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.liang.media.bean.MediaInfo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static android.support.constraint.Constraints.TAG;

public class SelectedCollection {

    public static final String STATE_SELECTION = "state_selection";
    private final Context mContext;
    private Set<MediaInfo> selectedMedias;

    public SelectedCollection(Context context) {
        mContext = context;
    }

    public void init(Bundle bundle) {
        if (bundle == null) {
            selectedMedias = new LinkedHashSet<>();
        } else {
            List<MediaInfo> medias = bundle.getParcelableArrayList(STATE_SELECTION);
            selectedMedias = new LinkedHashSet<>(medias);
        }
    }

    public void setDefaultSelection(List<MediaInfo> medias) {
        selectedMedias.addAll(medias);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(selectedMedias));
    }

    public boolean add(MediaInfo item) {
        boolean added = selectedMedias.add(item);
        Log.e(TAG, "add: added" + added);
        return added;
    }

    public boolean remove(MediaInfo mediaInfo) {
        return selectedMedias.remove(mediaInfo);
    }

    public ArrayList<MediaInfo> asList() {
        return new ArrayList<>(selectedMedias);
    }

    public ArrayList<Uri> asListOfUri() {
        ArrayList<Uri> uris = new ArrayList<>();
        for (MediaInfo info : selectedMedias) {
            uris.add(info.uri);
        }
        return uris;
    }

    public ArrayList<String> asListOfString() {
        ArrayList<String> paths = new ArrayList<>();
        for (MediaInfo info : selectedMedias) {
            paths.add(MediaUtils.getPath(mContext, info.uri));
        }
        return paths;
    }

    public boolean isEmpty() {
        return selectedMedias == null || selectedMedias.isEmpty();
    }

    public boolean isSelected(MediaInfo mediaInfo) {
        return selectedMedias.contains(mediaInfo);
    }


    public boolean maxSelectableReached() {
        return selectedMedias.size() == 10;
    }

    public void clear(){
        selectedMedias.clear();
    }


    public int count() {
        return selectedMedias.size();
    }

    public int checkedNumOf(MediaInfo mediaInfo) {
        int index = new ArrayList<>(selectedMedias).indexOf(mediaInfo);
        return index == -1 ? 0 : index + 1;
    }
}
