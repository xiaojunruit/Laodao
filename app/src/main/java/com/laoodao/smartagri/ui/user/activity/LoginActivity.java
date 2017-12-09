package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.Login3rdEvent;
import com.laoodao.smartagri.event.LoginWebEvent;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.user.contract.LoginContract;
import com.laoodao.smartagri.ui.user.presenter.LoginPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.laoodao.smartagri.view.IconButton;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/15.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {
    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_username)
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_password)
    @Password(scheme = Password.Scheme.ANY, messageResId = R.string.please_input_correct_password_format)
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_show_pwd)
    ImageView mBtnShowPwd;
    @BindView(R.id.btn_login)
    RoundTextView mBtnLogin;
    @BindView(R.id.btn_forget)
    TextView mBtnForget;
    @BindView(R.id.btn_register)
    RoundTextView mBtnRegister;
    @BindView(R.id.ib_weixin)
    IconButton mIbWeixin;
    @BindView(R.id.ib_qq)
    IconButton mIbQq;
    QQSdk mQQSdk;
    WechatSdk mWechatSdk;
    String mark;
    private DefaultValidator mValidator;
    private boolean isWeb;

    public static void start(Context context, boolean isWeb) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isWeb", isWeb);
        UiUtils.startActivity(context, LoginActivity.class, bundle);
    }


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
        isWeb = getIntent().getBooleanExtra("isWeb", false);
        mValidator = new DefaultValidator(this);
        mValidator.clicked(mBtnLogin).succeeded(() -> {
            String username = mEtUsername.getText().toString();
            String pwd = mEtPassword.getText().toString();
            mPresenter.login(username, pwd, LDApplication.getPushId());
        });
    }

    @Override
    protected void onDestroy() {
        if (isWeb) {
            EventBus.getDefault().post(new LoginWebEvent());
        }
        super.onDestroy();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login3rd(Login3rdEvent event) {
        if (mWechatSdk != null) {
            mWechatSdk.onActivityResult(event.requestCode, event.resultCode, event.data);
        }
        if (mQQSdk != null) {
            mQQSdk.onActivityResult(event.requestCode, event.resultCode, event.data);
        }
    }


    @OnClick({R.id.btn_show_pwd, R.id.btn_forget, R.id.btn_register, R.id.ib_weixin, R.id.ib_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_pwd:
                UiUtils.hideOrShowPassword(mBtnShowPwd, mEtPassword);
                break;
            case R.id.btn_forget:
                UiUtils.startActivity(this, ForgetPwdActivity.class);
                break;
            case R.id.btn_register:
                UiUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.ib_weixin:
                if (mWechatSdk == null) {
                    mWechatSdk = new WechatSdk(this, BuildConfig.APP_ID_WECHAT);
                }
                mWechatSdk.authorize(info -> {
                    mark = "微信";
                    LogUtil.e("wechat ===> token = " + info.code + ", state = " + info.state);
                    mPresenter.login3rd("wechat", "", info.code, "微信登录中...");
                });
                break;
            case R.id.ib_qq:
                if (mQQSdk == null) {
                    mQQSdk = new QQSdk(this, BuildConfig.APP_ID_QQ);
                }
                mQQSdk.authorize(info -> {
                    mark = "QQ";
                    LogUtil.e("qq ===> token = " + info.getAccessToken());
                    mPresenter.login3rd("qq", info.getOpenId(), info.getAccessToken(), "QQ登陆中...");
                });
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getExtras() == null) {
            return;
        }
        EventBus.getDefault().post(new Login3rdEvent(requestCode, resultCode, data));
    }

    @Override
    public void loginSuccess(User user) {
        Global.getInstance().setUserInfo(user).setToken(user.token);
        Global.getInstance().markUserInfoChange();
        if (isWeb) {
            finish();
        } else {
            finish();
            MainActivity.start(this, 4);
        }
    }

    @Override
    public void login3rdSuccess(User user) {
        Global.getInstance().setUserInfo(user).setToken(user.token);
        Global.getInstance().markUserInfoChange();
        finish();
    }

    @Override
    public void jointLogin(String avatar, String nickName, String platform, String openId, String accessToken, String tips) {
        JointLoginActivity.start(this, accessToken, platform, openId, tips, avatar, nickName, mark);
        finish();
    }

}
