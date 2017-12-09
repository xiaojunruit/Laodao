package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.user.contract.RegisterContract;
import com.laoodao.smartagri.ui.user.presenter.RegisterPresenter;
import com.laoodao.smartagri.utils.CodeCountDown;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/15.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.RegisterView {
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
    private DefaultValidator mValidator;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
        mValidator = new DefaultValidator(this);
        mValidator.clicked(mBtnRegister).succeeded(() -> {
            String phone = mEtPhone.getText().toString();
            String pwd = mEtPwd.getText().toString();
            String code = mEtAuthCode.getText().toString();
            mPresenter.register(phone, pwd, code);
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
    public void registerSuccess(User user) {
        Global.getInstance().setUserInfo(user).setToken(user.token);
        Global.getInstance().markUserInfoChange();
        MainActivity.start(this, 4);
        finish();
    }
}
