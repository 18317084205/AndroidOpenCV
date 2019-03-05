package com.liang.media.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liang.media.R;
import com.liang.media.bean.MediaFolder;
import com.liang.media.loader.FolderCursorLoader;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener,
        FoldersFragment.OnFolderSelectedListener {

    private static final String TAG = SelectorActivity.class.getSimpleName();
    private static final String CURRENT_FRAGMENT_KEY = "current_fragment_key";
    private static final String TITLE_KEY = "title_key";

    private Fragment mCurrentFragment;

    private TextView toolbar_title;
    private Button toolbar_back;
    private TextView toolbar_close;
    private MediaFragment mMediaFragment;
    private FoldersFragment mFoldersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        initView();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle bundle) {
        if (bundle != null) {
            toolbar_title.setText(bundle.getString(TITLE_KEY, getString(R.string.folder_all)));
            mMediaFragment = (MediaFragment) getSupportFragmentManager()
                    .findFragmentByTag(MediaFragment.class.getSimpleName());

            mFoldersFragment = (FoldersFragment) getSupportFragmentManager()
                    .findFragmentByTag(FoldersFragment.class.getSimpleName());
            if (mFoldersFragment != null) {
                mFoldersFragment.setFolderSelectedListener(this);
            }

            String currentFragmentTag = bundle.getString(CURRENT_FRAGMENT_KEY, CURRENT_FRAGMENT_KEY);
            Log.e(TAG, "initFragment: currentFragmentTag = " + currentFragmentTag);

            mCurrentFragment = getSupportFragmentManager()
                    .findFragmentByTag(currentFragmentTag);

            if (mCurrentFragment != null) {
                return;
            }
        }

        initMediaFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mCurrentFragment != null) {
            outState.putString(CURRENT_FRAGMENT_KEY, mCurrentFragment.getClass().getSimpleName());
            outState.putString(TITLE_KEY, toolbar_title.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private void initMediaFragment() {
        toolbar_title.setText(getString(R.string.folder_all));
        if (mMediaFragment == null) {
            mMediaFragment = MediaFragment.newInstance();
        }
        mMediaFragment.setFolderId(FolderCursorLoader.ALL_FOLDER_ID);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                switchFragment(mMediaFragment, getSupportFragmentManager().beginTransaction());
            }
        });
    }

    private void loadMediaFragment(String folderId) {
        if (mMediaFragment == null) {
            mMediaFragment = MediaFragment.newInstance();
        }

        mMediaFragment.setFolderId(folderId);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_right_in,
                        R.anim.slide_left_out);
        switchFragment(mMediaFragment, transaction);
    }

    private void loadFolderFragment() {
        if (mFoldersFragment == null) {
            mFoldersFragment = FoldersFragment.newInstance();
            mFoldersFragment.setFolderSelectedListener(this);
        }

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_left_in,
                        R.anim.slide_right_out);

        switchFragment(mFoldersFragment, transaction);
    }

    private void initView() {
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_back = findViewById(R.id.toolbar_back);
        toolbar_close = findViewById(R.id.toolbar_close);

        toolbar_back.setOnClickListener(this);
        toolbar_close.setOnClickListener(this);
    }

    private void loadMedia(MediaFolder mediaFolder) {
        toolbar_title.setText(mediaFolder.name);
        loadMediaFragment(mediaFolder.id);
    }

    private void switchFragment(Fragment targetFragment, FragmentTransaction transaction) {

        if (targetFragment == mCurrentFragment) {
            return;
        }

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }

        if (!targetFragment.isAdded()) {
            transaction.add(R.id.fg_content, targetFragment, targetFragment.getClass().getSimpleName());
        } else {
            transaction.show(targetFragment);
        }

        transaction.commitAllowingStateLoss();

        mCurrentFragment = targetFragment;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == toolbar_back.getId()) {
            loadFolderFragment();
        }

        if (v.getId() == toolbar_close.getId()) {
            finish();
        }
    }

    @Override
    public void onFolderSelected(MediaFolder mediaFolder) {
        loadMedia(mediaFolder);
    }
}
