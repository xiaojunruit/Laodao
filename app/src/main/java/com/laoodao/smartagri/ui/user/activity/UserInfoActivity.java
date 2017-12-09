package com.laoodao.smartagri.ui.user.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.tablayout.widget.MsgView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.user.contract.UserInfoContract;
import com.laoodao.smartagri.ui.user.presenter.UserInfoPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.UploadSinglePicture;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.UserInfoView {
    @BindView(R.id.img_avatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.avatar)
    LinearLayout mAvatar;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.nick_name)
    FrameLayout mNickName;
    @BindView(R.id.tv_true_name)
    TextView mTvTrueName;
    @BindView(R.id.fl_true_name)
    FrameLayout mFlTrueName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.sex)
    FrameLayout mSex;
    @BindView(R.id.tv_adress)
    TextView mTvAdress;
    @BindView(R.id.fl_address)
    LinearLayout mFlAddress;
    @BindView(R.id.tv_autograph)
    TextView mTvAutograph;
    @BindView(R.id.fl_introduction)
    LinearLayout mFlIntroduction;
    @BindView(R.id.msg_dot)
    MsgView mMsgDot;
    @BindView(R.id.tv_authentication_state)
    TextView mTvAuthenticationState;
    @BindView(R.id.fl_authentication_state)
    FrameLayout mFlAuthenticationState;
    //    private Uri uri;
    private NormalListDialog mSexSelectDialog;
    private RxPermissions mRxPresmission;
    User user;
    private UploadSinglePicture mUploadSinglePicture;
    private String mCityId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mRxPresmission = new RxPermissions(this);
        mUploadSinglePicture = new UploadSinglePicture(this);
//        try {
//            uri = Uri.fromFile(File.createTempFile("laoodao", ".jpeg", getExternalCacheDir()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        user = Global.getInstance().getUserInfo();
        Glide.with(this).load(user.avatar).into(mImgAvatar);
        mTvUsername.setText(user.uid);
        mTvNickname.setText(user.nickname);
        mTvSex.setText(user.sex);
        mTvAutograph.setText(user.signature);
        mTvAuthenticationState.setText(user.memberTypeName1);
        mTvAdress.setText(user.local);
        mTvTrueName.setText(user.truename);
        if (Integer.parseInt(user.memberType) != 0) {
            mMsgDot.setVisibility(View.GONE);
        }
        mUploadSinglePicture.setOnUploadListener(file -> {
            mPresenter.updateAvatar(file);
        });
    }

    @Override
    public void complete() {

    }

    @OnClick({R.id.avatar, R.id.nick_name, R.id.sex, R.id.fl_address, R.id.fl_introduction, R.id.fl_true_name, R.id.fl_authentication_state})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
//                avatarShareDialog();
                mUploadSinglePicture.avatarShareDialog();
                break;
            case R.id.nick_name:
                UiUtils.startActivity(this, UpdateNickNameActivity.class);
                break;
            case R.id.sex:
                showSexDialog();
                break;
            case R.id.fl_address:
                UiUtils.startActivity(AreaSelectorActivity.class);
                break;
            case R.id.fl_introduction:
                UiUtils.startActivity(IntroductionActivity.class);
                break;
            case R.id.fl_true_name:
                Log.e("))))))))))))",user.memberType);
                if ("0".equals(user.memberType) || "5".equals(user.memberType)) {
                    UiUtils.startActivity(UpdateTrueNameActivity.class);
                }
                break;
            case R.id.fl_authentication_state:
                ApplyCertificationActivity.start(this, Integer.parseInt(user.memberType));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = Global.getInstance().getUserInfo();
        mTvAutograph.setText(user.signature);
        mTvAuthenticationState.setText(user.memberTypeName1);
        mTvTrueName.setText(user.truename);
        if (Integer.parseInt(user.memberType) != 0) {
            mMsgDot.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void selectCity(SelectAreaEvent event) {
        String address = "";
        if (event.province != null) {
            address = event.province.name;
            mCityId = String.valueOf(event.province.id);
        }
        if (event.city != null) {
            address += event.city.name;
            mCityId = String.valueOf(event.city.id);
        }

        if (event.county != null) {
            address += event.county.name;
            mCityId = String.valueOf(event.county.id);
        }
        if (event.town != null) {
            address += event.town.name;
            mCityId = String.valueOf(event.town.id);
        }
        mTvAdress.setText(address);
        mPresenter.updateArea(mCityId);
    }


//    private void avatarShareDialog() {
//        AvatarSelectDialog dialog = new AvatarSelectDialog(this);
//        dialog.setOnAvatarListener((view1) -> {
//            switch (view1.getId()) {
//                case R.id.btn_camera:
//                    mPresenter.requestCarmeraPermission(mRxPresmission);
//                    break;
//                case R.id.btn_photo:
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, Constant.CODE_GALLERY_REQUEST);
//                    break;
//            }
//        });
//        dialog.show();
//    }
//
//
//    public static String getPath(Context ctx, Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = ctx.getContentResolver().query(uri, projection, null, null, null);
//        if (cursor == null) {
//            return null;
//        }
//        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String s = cursor.getString(columnIndex);
//        cursor.close();
//        return s;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUploadSinglePicture.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case Constant.CODE_CAMERA_REQUEST:
//                crop(uri, uri, 400, 400, Constant.CROP_BIG_PICTURE);
//                break;
//            case Constant.CODE_GALLERY_REQUEST:
//                crop(data.getData(), uri, 400, 400, Constant.CROP_BIG_PICTURE);
//                break;
//            case Constant.CROP_BIG_PICTURE:
//                upload(uri);
//                break;
//            case Constant.CODE_NICKNAME:
//                break;
//            case Constant.CODE_PHONE:
//                break;
//        }
    }

//    File toFile(Uri uri) {
//        File file;
//        if (uri.getScheme().equals("content")) {
//            file = new File(getPath(this, uri));
//        } else {
//            file = new File(uri.getPath());
//        }
//        return file;
//    }

//    private void upload(Uri uri) {
//        mPresenter.updateAvatar(toFile(uri));
//    }

//    private void crop(Uri in, Uri out, int width, int height, int requestCode) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(in, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
////        intent.putExtra("outputX", width);
////        intent.putExtra("outputY", height);
//        intent.putExtra("scale", false);
//        intent.putExtra("return-data", false);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
//        startActivityForResult(intent, requestCode);
//    }
//
//
//    public static Intent photoCapture(Uri uri) {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra("output", uri);
//        return intent;
//    }


    @Override
    public void avatarSuccess(String avatar) {
        User user = Global.getInstance().getUserInfo();
        user.avatar = avatar;
        Global.getInstance().setUserInfo(user).markUserInfoChange();
        Glide.with(this).load(avatar).into(mImgAvatar);
    }

    @Override
    public void updateSexSuccess(int sex) {
        User user = Global.getInstance().getUserInfo();
        String[] sexList = UiUtils.getStringArray(R.array.sex_array);
        user.sex = sexList[sex];
        mTvSex.setText(user.sex);
        Global.getInstance().setUserInfo(user).markUserInfoChange();

    }

    @Override
    public void updateAreaSuccess(String area) {
        User user = Global.getInstance().getUserInfo();
        user.local = area;
        Global.getInstance().setUserInfo(user).markUserInfoChange();
    }

    @Override
    public void requestPermissionSuccess() {
//        startActivityForResult(photoCapture(uri), Constant.CODE_CAMERA_REQUEST);
    }

    public void showSexDialog() {
        if (mSexSelectDialog == null) {
            String[] sexList = UiUtils.getStringArray(R.array.sex_array);
            mSexSelectDialog = new NormalListDialog(this, sexList);
            mSexSelectDialog.dividerColor(UiUtils.getColor(R.color.common_divider_narrow));
            mSexSelectDialog.titleBgColor(UiUtils.getColor(R.color.colorAccent));
            mSexSelectDialog.isTitleShow(true);
            mSexSelectDialog.title(UiUtils.getString(R.string.sex_title));
            mSexSelectDialog.setOnOperItemClickL((parent, view, position, id) -> {
                mPresenter.updateSex(position);
                mSexSelectDialog.dismiss();
            });
            mSexSelectDialog.layoutAnimation(null);
        }
        mSexSelectDialog.show();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateNickName(UserInfoChangedEvent event) {
        user = Global.getInstance().getUserInfo();
        mTvNickname.setText(user.nickname);
    }


}
