package com.laoodao.smartagri.ui.user.fragment;

import android.view.KeyEvent;
import android.widget.EditText;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.SettingPwdContract;
import com.laoodao.smartagri.ui.user.presenter.SettingPwdPresenter;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/21.
 */

public class SettingPwdFragment extends BaseFragment<SettingPwdPresenter> implements SettingPwdContract.SettingPwdView {
    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_password)
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_confirm_password)
    @BindView(R.id.et_repeat_pwd)
    EditText mEtRepeatPwd;
    @BindView(R.id.btn_submit)
    RoundTextView mBtnSubmit;
    private String token;
    private DefaultValidator mValidator;

    @Override
    public void complete() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_setting_pwd;
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
        mValidator.clicked(mBtnSubmit).succeeded(() -> {
            String pwd = mEtPwd.getText().toString().replaceAll("\\s", "");
            String rePwd = mEtRepeatPwd.getText().toString().replaceAll("\\s", "");
            mPresenter.findPassword(token, pwd, rePwd);
        });
        mEtPwd.setOnEditorActionListener((v, actionId, event) -> {
            return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
        });
        mEtRepeatPwd.setOnEditorActionListener((v, actionId, event) -> {
            return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
        });
    }


    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void settingSuccess() {
        getActivity().finish();

    }
}
