package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
import com.laoodao.smartagri.ui.user.contract.FastRegisterContract;
import com.laoodao.smartagri.ui.user.presenter.FastRegisterPresenter;
import com.laoodao.smartagri.utils.CodeCountDown;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/4.
 */

public class FastRegisterActivity extends BaseActivity<FastRegisterPresenter> implements FastRegisterContract.FastRegisterView {

    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_phone)
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_code)
    @BindView(R.id.et_auth_code)
    EditText mEtAuthCode;
    @BindView(R.id.btn_send_code)
    RoundTextView mBtnSendCode;
    @Order(3)
    @NotEmpty(messageResId = R.string.please_input_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.btn_show_pwd)
    ImageView mBtnShowPwd;
    @BindView(R.id.btn_check)
    CheckBox mBtnCheck;
    @BindView(R.id.btn_register)
    RoundTextView mBtnRegister;
    @BindView(R.id.tv_bind_register)
    TextView mTvBindRegister;
    private DefaultValidator mValidator;
    private String token;
    private String platform;
    private String openId;
    private String tips;

    public static void start(Context context, String token, String platform, String openId, String tips) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("platform", platform);
        bundle.putString("openId", openId);
        bundle.putString("tips", tips);
        UiUtils.startActivity(context, FastRegisterActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fast_register;
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
        mValidator = new DefaultValidator(this);
        mValidator.clicked(mBtnRegister).succeeded(() -> {
            String phone = mEtPhone.getText().toString();
            String pwd = mEtPwd.getText().toString();
            String code = mEtAuthCode.getText().toString();
            mPresenter.register(token, platform, openId, tips, phone, pwd, code, Constant.REGISTER_ACCOUNT);
        });
    }

    @OnClick({R.id.btn_show_pwd, R.id.btn_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                String phone = mEtPhone.getText().toString();
                mPresenter.getCode(phone, Constant.REG);
                break;
            case R.id.btn_show_pwd:
                UiUtils.hideOrShowPassword(mBtnShowPwd, mEtPwd);
                break;
        }
    }

    @Override
    public void countdown() {
        new CodeCountDown(mBtnSendCode).start();
    }

    @Override
    public void registerSuccess(User data) {
        Global.getInstance().setUserInfo(data).setToken(data.token);
        Global.getInstance().markUserInfoChange();
        MainActivity.start(this, 4);
        finish();
    }
}
