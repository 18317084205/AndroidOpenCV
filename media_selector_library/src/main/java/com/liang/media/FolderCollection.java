package com.liang.media;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.liang.media.loader.FolderCursorLoader;

import java.lang.ref.WeakReference;

public class FolderCollection implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private LoaderCallbacks mCallbacks;
    private boolean mLoadFinished;

    public FolderCollection(FragmentActivity activity, LoaderCallbacks callbacks) {
        mContext = new WeakReference<Context>(activity);
        mLoaderManager = LoaderManager.getInstance(activity);
        mCallbacks = callbacks;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        mLoadFinished = false;
        return FolderCursorLoader.newInstance(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        if (!mLoadFinished) {
            mLoadFinished = true;
            mCallbacks.onFolderLoadFinished(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onFolderLoaderReset();
    }


    public void onDestroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
        mCallbacks = null;
    }

    public void loadFolders() {
        mLoaderManager.initLoader(LOADER_ID, null, this);
    }

    public interface LoaderCallbacks {
        void onFolderLoadFinished(Cursor cursor);

        void onFolderLoaderReset();
    }
}
