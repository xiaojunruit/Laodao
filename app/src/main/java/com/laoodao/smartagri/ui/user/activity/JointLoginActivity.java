package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.JointLoginContract;
import com.laoodao.smartagri.ui.user.presenter.JointLoginPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WORK on 2017/4/28.
 */

public class JointLoginActivity extends BaseActivity<JointLoginPresenter> implements JointLoginContract.JointLoginView {


    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_register)
    RoundTextView mTvRegister;
    @BindView(R.id.tv_joint)
    TextView mTvJoint;

    private String token;
    private String platform;
    private String openId;
    private String tips;
    private String avatar;
    private String nickName;
    private String mark;

    public static void start(Context context, String token, String platform, String openId, String tips, String avatar, String nickName,String mark) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("platform", platform);
        bundle.putString("openId", openId);
        bundle.putString("tips", tips);
        bundle.putString("avatar", avatar);
        bundle.putString("nickName", nickName);
        bundle.putString("mark",mark);
        UiUtils.startActivity(context, JointLoginActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joint_login;
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
        token = getIntent().getStringExtra("token");
        platform = getIntent().getStringExtra("platform");
        openId = getIntent().getStringExtra("openId");
        tips = getIntent().getStringExtra("tips");
        avatar = getIntent().getStringExtra("avatar");
        nickName = getIntent().getStringExtra("nickName");
        mark=getIntent().getStringExtra("mark");
        mTvName.setText(UiUtils.getString(R.string.joint_name,mark,nickName));
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(this).load(avatar).into(mAvatar);
        }
    }

    @OnClick({R.id.tv_register, R.id.tv_joint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                FastRegisterActivity.start(this, token, platform, openId, tips);
                break;
            case R.id.tv_joint:
                BindLoginActivity.start(this, token, platform, openId, tips,mark);
                break;
        }
    }
}
