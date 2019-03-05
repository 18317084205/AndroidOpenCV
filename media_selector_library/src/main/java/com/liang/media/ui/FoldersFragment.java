package com.liang.media.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liang.media.FolderCollection;
import com.liang.media.R;
import com.liang.media.adapter.FolderAdapter;
import com.liang.media.bean.MediaFolder;

public class FoldersFragment extends Fragment implements FolderCollection.LoaderCallbacks,
        FolderAdapter.OnItemClickListener {

    private RecyclerView mFoldersList;

    private FolderAdapter mFolderAdapter;
    private OnFolderSelectedListener mOnFolderSelectedListener;

    private FolderCollection mFolderCollection;

    public static FoldersFragment newInstance() {
        return new FoldersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.folders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFoldersList = view.findViewById(R.id.album_list);
        mFoldersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFolderAdapter = new FolderAdapter();
        mFolderAdapter.setOnItemClickListener(this);
        mFoldersList.setAdapter(mFolderAdapter);

        loadFolders();
    }

    private void loadFolders() {
        mFolderCollection = new FolderCollection(getActivity(), this);
        mFolderCollection.loadFolders();
    }

    public void setFolderSelectedListener(OnFolderSelectedListener folderSelectedListener) {
        this.mOnFolderSelectedListener = folderSelectedListener;
    }

    @Override
    public void onItemClick(MediaFolder mediaFolder) {
        if (mOnFolderSelectedListener != null) {
            mOnFolderSelectedListener.onFolderSelected(mediaFolder);
        }
    }

    @Override
    public void onFolderLoadFinished(Cursor cursor) {
        mFolderAdapter.swapCursor(cursor);
    }

    @Override
    public void onFolderLoaderReset() {
        mFolderAdapter.swapCursor(null);
    }

    public interface OnFolderSelectedListener {
        void onFolderSelected(MediaFolder mediaFolder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFolderCollection.onDestroy();
    }
}
