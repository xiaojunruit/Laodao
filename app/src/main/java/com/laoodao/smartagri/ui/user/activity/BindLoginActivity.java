package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.user.contract.BindLoginContract;
import com.laoodao.smartagri.ui.user.presenter.BindLoginPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/28.
 */

public class BindLoginActivity extends BaseActivity<BindLoginPresenter> implements BindLoginContract.BindLoginView {
    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_username)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_show_pwd)
    ImageView mBtnShowPwd;
    @BindView(R.id.tv_mark)
    TextView mTvMark;
    @BindView(R.id.tv_submit)
    RoundTextView mTvSubmit;
    private DefaultValidator mValidator;
    private String token;
    private String platform;
    private String openId;
    private String tips;
    private String mark;

    public static void start(Context context, String token, String platform, String openId, String tips, String mark) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("platform", platform);
        bundle.putString("openId", openId);
        bundle.putString("tips", tips);
        bundle.putString("mark", mark);
        UiUtils.startActivity(context, BindLoginActivity.class, bundle);
    }


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_login;
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
        mark = getIntent().getStringExtra("mark");
        mValidator = new DefaultValidator(this);
        mValidator.clicked(mTvSubmit).succeeded(() -> {
            String phone = mEtPhone.getText().toString();
            String pwd = mEtPassword.getText().toString();
            mPresenter.requestBindLogin(platform, openId, token, tips, Constant.JOINT_ACCOUNT, phone, "", pwd);
        });
        mTvMark.setText(UiUtils.getString(R.string.bind_account, mark));
    }

    @OnClick({R.id.btn_show_pwd, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_pwd:
                UiUtils.hideOrShowPassword(mBtnShowPwd, mEtPassword);
                break;
            case R.id.tv_submit:
                break;
        }
    }

    @Override
    public void bindLoginSuccess(User user) {
        Global.getInstance().setUserInfo(user).setToken(user.token);
        Global.getInstance().markUserInfoChange();
        MainActivity.start(this, 4);
        finish();
    }
}
