package com.laoodao.smartagri.ui.user.activity;

import android.widget.EditText;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.UpdatePhoneContract;
import com.laoodao.smartagri.ui.user.presenter.UpdatePhonePresenter;
import com.laoodao.smartagri.utils.CodeCountDown;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/15.
 * 绑定手机号码Activity
 */

public class UpdatePhoneActivity extends BaseActivity<UpdatePhonePresenter> implements UpdatePhoneContract.UpdatePhoneView {
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
    @BindView(R.id.btn_submit)
    RoundTextView mBtnSubmit;
    private DefaultValidator mValidator;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
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
        mValidator.clicked(mBtnSubmit).succeeded(() -> {
            String phone=mEtPhone.getText().toString();
            String code=mEtAuthCode.getText().toString();
            mPresenter.requestUpdatePhone(phone,code);
        });
    }

    @OnClick(R.id.btn_send_code)
    public void onClick() {
        String phone = mEtPhone.getText().toString();
        mPresenter.requestCode(phone);
    }

    @Override
    public void codeSuccess() {
        new CodeCountDown(mBtnSendCode).start();
    }

    @Override
    public void updatePhoneSuccess(String phone) {
        User user = Global.getInstance().getUserInfo();
        user.mobile = phone;
        Global.getInstance().setUserInfo(user);
        Global.getInstance().markUserInfoChange();
    }
}
