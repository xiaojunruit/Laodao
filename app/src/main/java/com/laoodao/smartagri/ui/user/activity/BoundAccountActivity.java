package com.laoodao.smartagri.ui.user.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.Login3rdEvent;
import com.laoodao.smartagri.event.UpdatePwdEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.user.contract.BoundAccountContract;
import com.laoodao.smartagri.ui.user.presenter.BoundAccountPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/15.
 * 账号与密码Activity
 */

public class BoundAccountActivity extends BaseActivity<BoundAccountPresenter> implements BoundAccountContract.BoundAccountView {
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.phone)
    FrameLayout mPhone;
    @BindView(R.id.pwd)
    FrameLayout mPwd;
    @BindView(R.id.wx)
    LinearLayout mWx;
    @BindView(R.id.qq)
    LinearLayout mQq;
    @BindView(R.id.tv_wx_state)
    TextView mTvWxState;
    @BindView(R.id.tv_qq_state)
    TextView mTvQqState;
    QQSdk mQQSdk;
    WechatSdk mWechatSdk;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bound_account;
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
        User user = Global.getInstance().getUserInfo();
        if (user == null) return;
        mTvPhone.setText(user.mobile);
        mTvWxState.setText(!user.isWxBind ? "未绑定" : user.wxBindName);
        mTvQqState.setText(!user.isQqBind ? "未绑定" : user.qqBindName);
    }

    @OnClick({R.id.phone, R.id.pwd, R.id.wx, R.id.qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone:
                UiUtils.startActivity(this, UpdatePhoneActivity.class);
                break;
            case R.id.pwd:
                UiUtils.startActivity(this, UpdatePwdActivity.class);
                break;
            case R.id.wx:
                bindLogin(false, Constant.WECHAT);
                break;
            case R.id.qq:
                bindLogin(true, Constant.QQ_SMALL);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePwd(UpdatePwdEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePhone(UserInfoChangedEvent event) {
        User user = Global.getInstance().getUserInfo();
        mTvPhone.setText(user.mobile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getExtras() == null) {
            return;
        }

        if (mQQSdk != null) {
            mQQSdk.onActivityResult(requestCode, resultCode, data);
        }
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
    }

    private void bindLogin(boolean isQQ, String platForm) {
        String bind = "";
        User user = Global.getInstance().getUserInfo();
        if (isQQ) {
            bind = user.isQqBind ? "解绑" : "绑定";
        } else {
            bind = user.isWxBind ? "解绑" : "绑定";
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("是否" + bind + "" + (isQQ ? "QQ" : "微信") + "？");
        builder.setPositiveButton("确定", ((dialog1, which) -> {
            if (Constant.QQ_SMALL.equals(platForm)) {
                qqAccredit();
            } else {
                wechatAccredit();
            }
        }));
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    //qq授权
    private void qqAccredit() {
        User user = Global.getInstance().getUserInfo();
        //解绑
        if (user.isQqBind) {
            mPresenter.requestThird(Constant.QQ_SMALL, "", "", Constant.UN_BIND);
            return;
        }
        if (UiUtils.isFastClick(1000)) {
            return;
        }
        //绑定
        if (mQQSdk == null) {
            mQQSdk = new QQSdk(this, BuildConfig.APP_ID_QQ);
        }
        mQQSdk.authorize(info -> {
            LogUtil.e("qq ===> token = " + info.getAccessToken() + ", id = " + info.getOpenId());
            mPresenter.requestThird(Constant.QQ_SMALL, info.getOpenId(), info.getAccessToken(), Constant.SU_BIND);
        });
    }

    //微信授权
    private void wechatAccredit() {
        User user = Global.getInstance().getUserInfo();
        //解绑
        if (user.isWxBind) {
            mPresenter.requestThird(Constant.WECHAT, "", "", Constant.UN_BIND);
            return;
        }
        //绑定
        if (UiUtils.isFastClick(1000)) {
            return;
        }
        if (mWechatSdk == null) {
            mWechatSdk = new WechatSdk(this, BuildConfig.APP_ID_WECHAT);
        }
        mWechatSdk.authorize(info -> {
            LogUtil.e("wechat ===> token = " + info.code + ", state = " + info.state);
            mPresenter.requestThird(Constant.WECHAT, "", info.code, Constant.SU_BIND);
        });
    }

    @Override
    public void thirdSuccess(String platform, String nickName) {
        User user = Global.getInstance().getUserInfo();
        if (Constant.QQ_SMALL.equals(platform)) {
            user.isQqBind = !user.isQqBind;
            user.qqBindName = nickName;
            mTvQqState.setText(user.isQqBind ? user.qqBindName : "未绑定");
        } else {
            user.isWxBind = !user.isWxBind;
            user.wxBindName = nickName;
            mTvWxState.setText(user.isWxBind ? user.wxBindName : "未绑定");
        }
        Global.getInstance().setUserInfo(user);
    }

}
