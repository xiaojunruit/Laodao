package com.laoodao.smartagri.ui.user.activity;

import android.content.Intent;
import android.widget.EditText;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.UpdatePwdEvent;
import com.laoodao.smartagri.ui.user.contract.UpdatePwdContract;
import com.laoodao.smartagri.ui.user.presenter.UpdatePwdPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/15.
 */

public class UpdatePwdActivity extends BaseActivity<UpdatePwdPresenter> implements UpdatePwdContract.UpdatePwdView {

    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_old_pwd)
    EditText mEtOldPwd;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_new_pwd)
    EditText mEtNewPwd;
    @Order(3)
    @NotEmpty(messageResId = R.string.please_input_confirm_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_re_new_pwd)
    EditText mEtReNewPwd;
    @BindView(R.id.btn_submit)
    RoundTextView mBtnSubmit;
    private DefaultValidator mValidator;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
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
            String oldPwd = mEtOldPwd.getText().toString();
            String newPwd = mEtNewPwd.getText().toString();
            String reNewPwd = mEtReNewPwd.getText().toString();
            mPresenter.postPwdData(oldPwd, newPwd, reNewPwd);
        });
    }



    @Override
    public void updatePwdSuccess() {
        Global.getInstance().logout();
        UiUtils.startActivity(LoginActivity.class);
        EventBus.getDefault().post(new UpdatePwdEvent());
        finish();
    }
}
