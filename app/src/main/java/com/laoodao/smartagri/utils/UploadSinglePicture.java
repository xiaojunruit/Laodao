package com.laoodao.smartagri.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.dialog.AvatarSelectDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by WORK on 2017/7/27.
 */

public class UploadSinglePicture {
    private Uri uri;
    private Context mContext;
    private RxPermissions mRxPermissions;
    private UploadListener mUploadListener;
    private boolean misShear = true;

    public UploadSinglePicture(Context context) {
        this.mContext = context;
        try {
            uri = Uri.fromFile(File.createTempFile("laoodao", ".jpeg", mContext.getExternalCacheDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRxPermissions = new RxPermissions(((Activity) mContext));
    }

    public UploadSinglePicture(Context context, boolean isShear) {
        this.mContext = context;
        misShear = isShear;
        try {
            uri = Uri.fromFile(File.createTempFile("laoodao", ".jpeg", mContext.getExternalCacheDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRxPermissions = new RxPermissions(((Activity) mContext));
    }

    public void setOnUploadListener(UploadListener listener) {
        this.mUploadListener = listener;
    }

    private String getPath(Context ctx, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ctx.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(columnIndex);
        cursor.close();
        return s;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.CODE_CAMERA_REQUEST:
                if (misShear) {
                    crop(uri, uri, Constant.CROP_BIG_PICTURE);
                } else {
                    uploadUri(uri);
                }
                break;
            case Constant.CODE_GALLERY_REQUEST:
                if (misShear) {
                    crop(data.getData(), uri, Constant.CROP_BIG_PICTURE);
                } else {
                    uploadUri(data.getData());
                }
                break;
            case Constant.CROP_BIG_PICTURE:
                uploadUri(uri);
                break;
            case Constant.CODE_NICKNAME:
                break;
            case Constant.CODE_PHONE:
                break;
        }
    }

    private File toFile(Uri uri) {
        File file;
        if (uri.getScheme().equals("content")) {
            file = new File(getPath(mContext, uri));
        } else {
            file = new File(uri.getPath());
        }
        return file;
    }

    private void uploadUri(Uri uri) {
        mUploadListener.onUploadListener(toFile(uri));
    }


    private void crop(Uri in, Uri out, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(in, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", false);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
        ((Activity) mContext).startActivityForResult(intent, requestCode);

    }


    private Intent photoCapture(Uri uri) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        return intent;
    }

    public void avatarShareDialog() {
        AvatarSelectDialog dialog = new AvatarSelectDialog(mContext);
        dialog.setOnAvatarListener((view1) -> {
            switch (view1.getId()) {
                case R.id.btn_camera:
                    requestCarmeraPermission(mRxPermissions);
                    break;
                case R.id.btn_photo:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((Activity) mContext).startActivityForResult(intent, Constant.CODE_GALLERY_REQUEST);
                    break;
            }
        });
        dialog.show();
    }

    private void requestCarmeraPermission(RxPermissions permissions) {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {
                ((Activity) mContext).startActivityForResult(photoCapture(uri), Constant.CODE_CAMERA_REQUEST);
            }

            @Override
            public void failure() {
                UiUtils.makeText("请求权限失败,请前往设置开启权限");
            }
        }, permissions);
    }

    public interface UploadListener {
        void onUploadListener(File file);
    }

}
