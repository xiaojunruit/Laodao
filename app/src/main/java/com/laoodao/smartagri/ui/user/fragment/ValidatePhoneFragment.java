package com.laoodao.smartagri.ui.user.fragment;

import android.os.Bundle;
import android.widget.EditText;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.activity.ForgetPwdActivity;
import com.laoodao.smartagri.ui.user.contract.ValidatePhoneContract;
import com.laoodao.smartagri.ui.user.presenter.ValidatePhonePresenter;
import com.laoodao.smartagri.utils.CodeCountDown;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/21.
 */

public class ValidatePhoneFragment extends BaseFragment<ValidatePhonePresenter> implements ValidatePhoneContract.ValidatePhoneView {

    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_phone)
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_code)
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_next)
    RoundTextView mBtnNext;
    @BindView(R.id.btn_send_code)
    RoundTextView mBtnSendCode;
    private DefaultValidator mValidator;

    @Override
    public void complete() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_validate_phone;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        mValidator = new DefaultValidator(this);
        mValidator.clicked(mBtnNext).succeeded(() -> {
            String phone = mEtPhone.getText().toString();
            String code = mEtCode.getText().toString();
            mPresenter.validateCode(phone, code);
        });
    }

    @OnClick(R.id.btn_send_code)
    public void onClick() {
        String phone = mEtPhone.getText().toString();
        mPresenter.getCode(phone, Constant.FPW);
    }

    @Override
    public void setToken(String token) {
        ((ForgetPwdActivity)mActivity).nextStep(token);
    }

    @Override
    public void countdown() {
        new CodeCountDown(mBtnSendCode).start();
    }
}
