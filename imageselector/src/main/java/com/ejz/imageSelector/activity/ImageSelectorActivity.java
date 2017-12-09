package com.ejz.imageSelector.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ejz.imageSelector.R;
import com.ejz.imageSelector.adapter.GridSpacingItemDecoration;
import com.ejz.imageSelector.adapter.ImageFolderAdapter;
import com.ejz.imageSelector.adapter.ImageListAdapter;
import com.ejz.imageSelector.bean.LocalMedia;
import com.ejz.imageSelector.bean.LocalMediaFolder;
import com.ejz.imageSelector.utils.FileUtils;
import com.ejz.imageSelector.utils.LocalMediaLoader;
import com.ejz.imageSelector.utils.ScreenUtils;
import com.ejz.imageSelector.widget.FolderWindow;
import com.jaeger.library.StatusBarUtil;

import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欧源 on 2017/4/22.
 */

public class ImageSelectorActivity extends AppCompatActivity implements View.OnClickListener, ImageListAdapter.OnImageSelectChangedListener {
    private RecyclerView mRecyclerView;
    private int mSpanCount = 3;
    private ImageListAdapter mAdapter;
    private ImageView mBtnBack;
    private TextView mTvDone;
    private TextView mTvPreview;
    private LinearLayout mFolderLayout;
    private FrameLayout mTitleView;
    private TextView mFolderName;
    private int maxSelectNum = 9;
    private final int SDK_PERMISSION_REQUEST = 127;
    public final static int REQUEST_IMAGE = 66;
    public final static int REQUEST_CAMERA = 67;

    public final static String BUNDLE_CAMERA_PATH = "CameraPath";
    public final static String EXTRA_MAX_SELECT_NUM = "MaxSelectNum";
    public final static String EXTRA_SHOW_CAMERA = "ShowCamera";
    public final static String REQUEST_OUTPUT = "outputList";
    private FolderWindow mFolderWindow;
    private RelativeLayout mBottom;
    private String mCameraPath;

    public static void start(Activity activity, int maxSelectNum, boolean showCamear) {
        Intent intent = new Intent(activity, ImageSelectorActivity.class);
        intent.putExtra(EXTRA_SHOW_CAMERA, showCamear);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        activity.startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.status), 0);
        maxSelectNum = getIntent().getIntExtra(EXTRA_MAX_SELECT_NUM, 9);
        if (savedInstanceState != null) {
            mCameraPath = savedInstanceState.getString(BUNDLE_CAMERA_PATH);
        }
        initView();
    }

    private void initView() {
        mFolderWindow = new FolderWindow(this);
        mFolderName = (TextView) findViewById(R.id.folder_name);
        mTitleView = (FrameLayout) findViewById(R.id.toolbar);
        mBottom = (RelativeLayout) findViewById(R.id.bottom);
        mFolderLayout = (LinearLayout) findViewById(R.id.folder_layout);
        mTvDone = (TextView) findViewById(R.id.tv_done);
        mTvPreview = (TextView) findViewById(R.id.tv_preview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mSpanCount, ScreenUtils.dip2px(this, 2), false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, mSpanCount));
        mAdapter = new ImageListAdapter(this, maxSelectNum);
        mRecyclerView.setAdapter(mAdapter);
        mBtnBack = (ImageView) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        new LocalMediaLoader(this).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                mFolderWindow.setData(folders);
                mAdapter.bindImages(folders.get(0).getImages());
            }
        });
        mAdapter.setOnImageSelectChangedListener(this);
        mTvDone.setOnClickListener(this);
        mFolderLayout.setOnClickListener(this);
        mFolderWindow.setOnItemClickListener(new ImageFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String folderName, List<LocalMedia> images) {
                mFolderName.setText(folderName);
                mAdapter.bindImages(images);
                mFolderWindow.dismiss();

            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_back) {
            finish();
        } else if (i == R.id.tv_done) {
            onSelectDone(mAdapter.getSelectedImageList());
        } else if (i == R.id.folder_layout) {
            if (mFolderWindow.isShowing()) {
                mFolderWindow.dismiss();
            } else {
                mFolderWindow.showAsDropDown(mTitleView);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_CAMERA_PATH, mCameraPath);
    }

    /**
     * 完成
     *
     * @param selectedImageList
     */
    private void onSelectDone(List<LocalMedia> selectedImageList) {
        onResult(selectedImageList);
    }

    public void onSelectDone(String path) {
        ArrayList<LocalMedia> images = new ArrayList<>();
        LocalMedia lm = new LocalMedia(path);
        images.add(lm);
        onResult(images);
    }

    //返回值
    private void onResult(List<LocalMedia> selectedImageList) {
        setResult(RESULT_OK, new Intent().putExtra(REQUEST_OUTPUT, (Serializable) selectedImageList));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // on take photo success
            if (requestCode == REQUEST_CAMERA) {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(mCameraPath))));
                onSelectDone(mCameraPath);
            }
        }
    }


    @Override
    public void onChange(List<LocalMedia> selectImages) {
        int size = selectImages.size();
        boolean enable = size != 0;
        mTvDone.setEnabled(enable);
        mTvPreview.setEnabled(enable);
        if (enable) {
            mTvDone.setText(getString(R.string.selected_done_text, String.valueOf(size), String.valueOf(maxSelectNum)));
            mTvPreview.setText(getString(R.string.selected_preview_text, String.valueOf(size)));
        } else {
            mTvDone.setText(getString(R.string.done_text));
            mTvPreview.setText(getString(R.string.preview_text));
        }
    }

    @Override
    public void onTakePhoto() {
        getPersimmions();

    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, SDK_PERMISSION_REQUEST);
            }else{
                startCamera();
            }
        }else{
            startCamera();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == SDK_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "获取权限失败~", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPictureClick(LocalMedia media, int position) {

    }

    public void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File cameraFile = FileUtils.createCameraFile(this);
            mCameraPath = cameraFile.getAbsolutePath();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }
}
