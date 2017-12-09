package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejz.update.UpdateAgent;
import com.ejz.update.UpdateInfo;
import com.ejz.update.UpdateListener;
import com.ejz.update.UpdateManager;
import com.flyco.roundview.RoundTextView;
import com.hyphenate.chat.EMClient;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.NewMessageEvent;
import com.laoodao.smartagri.event.UpdatePwdEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.user.contract.SettingContract;
import com.laoodao.smartagri.ui.user.presenter.SettingPresenter;
import com.laoodao.smartagri.utils.Cleaner;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.Packer;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.SettingView {
    @BindView(R.id.btn_info)
    LinearLayout mBtnInfo;
    @BindView(R.id.checkbox_push)
    CheckBox mCheckboxPush;
    @BindView(R.id.about)
    LinearLayout mAbout;
    @BindView(R.id.check_update)
    LinearLayout mCheckUpdate;
    @BindView(R.id.clean)
    LinearLayout mClean;
    @BindView(R.id.btn_logout)
    RoundTextView mBtnLogout;
    @BindView(R.id.bound_account)
    LinearLayout mBoundAccount;
    @BindView(R.id.img_avatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_cache)
    TextView mTvCache;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        UiUtils.startActivity(context, SettingActivity.class, bundle);
    }


    @Override
    protected void configViews() {
        title = getIntent().getStringExtra("title");
        setTitle(title);
        User user = Global.getInstance().getUserInfo();
        Glide.with(this).load(user.avatar).into(mImgAvatar);
        mTvNickname.setText(user.nickname);
        mCheckboxPush.setChecked(user.msgPushState.equals("1"));
        mTvVersion.setText(DeviceUtils.getVersionName(this));
        mTvCache.setText(Cleaner.getTotalCacheSize(this));
    }

    @Override
    public void complete() {

    }

    @OnClick({R.id.btn_info, R.id.checkbox_push, R.id.about, R.id.check_update, R.id.clean, R.id.btn_logout, R.id.bound_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bound_account:
                UiUtils.startActivity(this, BoundAccountActivity.class);
                break;
            case R.id.btn_info:
                UiUtils.startActivity(this, UserInfoActivity.class);
                break;
            case R.id.checkbox_push:
                requestPushData(mCheckboxPush.isChecked());
                break;
            case R.id.about:
                break;
            case R.id.check_update:
                checkUpdate();
                break;
            case R.id.clean:
                Cleaner.cleanAllCache(this);
                mTvCache.setText("0M");
                break;
            case R.id.btn_logout:
                EventBus.getDefault().post(new NewMessageEvent(false, false, false));
                mPresenter.logout();
                break;
        }
    }

    private void requestPushData(boolean isPush) {
        mPresenter.settingPush(isPush ? Constant.ACCEPT_PUSH : Constant.NOT_ACCEPT_PUSH);
    }


    @Override
    public void logoutSuccess() {
        Global.getInstance().logout();
        UiUtils.startActivity(LoginActivity.class);
        EMClient.getInstance().logout(true);
        finish();
    }

    @Override
    public void msgPushSuccess() {
        EventBus.getDefault().post(new UserInfoChangedEvent());
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePwd(UpdatePwdEvent event) {
        finish();
    }


    /**
     * 检查更新
     */
    private void checkUpdate() {
        UpdateAgent.setChannel(Packer.getMarket(this, BuildConfig.FLAVOR));
        UpdateAgent.setUpdateUrl(BuildConfig.APP_BASE_URL + "ld/checkUpdate");
        UpdateManager.update(this, (var1, var2) -> {
            UiUtils.makeText("已是最新版本");
        });
    }


}
