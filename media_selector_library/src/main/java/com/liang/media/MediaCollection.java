package com.liang.media;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.liang.media.loader.FolderCursorLoader;
import com.liang.media.loader.MediaCursorLoader;

import java.lang.ref.WeakReference;

public class MediaCollection implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MediaCollection.class.getSimpleName();
    private static final int LOADER_ID = 2;
    private static final String FOLDER_ID = "folder_id";
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private LoaderCallbacks mLoaderCallback;

    public MediaCollection(FragmentActivity activity, LoaderCallbacks callback) {
        mContext = new WeakReference<Context>(activity);
        mLoaderManager = LoaderManager.getInstance(activity);
        mLoaderCallback = callback;
    }

    public void onDestroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
        mLoaderCallback = null;
    }

    public void loadMedia(String folderId) {
        Bundle bundle = new Bundle();
        bundle.putString(FOLDER_ID, folderId);
        mLoaderManager.initLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }

        String id = bundle.getString(FOLDER_ID, FolderCursorLoader.ALL_FOLDER_ID);

        return MediaCursorLoader.newInstance(context, id);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Context context = mContext.get();
        if (context == null || cursor == null || mLoaderCallback == null) {
            return;
        }
        mLoaderCallback.onMediaLoadFinished(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null || mLoaderCallback == null) {
            return;
        }
        mLoaderCallback.onMediaLoaderReset();
    }

    public interface LoaderCallbacks {
        void onMediaLoadFinished(Cursor cursor);

        void onMediaLoaderReset();
    }
}
