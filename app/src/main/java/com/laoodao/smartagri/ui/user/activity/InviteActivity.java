package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.InviteInfo;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.user.contract.InviteContract;
import com.laoodao.smartagri.ui.user.presenter.InvitePresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Long-PC on 2017/4/13.
 * 分享邀请
 */

public class InviteActivity extends BaseActivity<InvitePresenter> implements InviteContract.InviteView {


    @BindView(R.id.btn_invite)
    RoundTextView mBtnInvite;
    @BindView(R.id.btn_detail)
    TextView mBtnDetail;
    @BindView(R.id.btn_wx)
    ImageView mBtnWx;
    @BindView(R.id.btn_friend)
    ImageView mBtnFriend;
    @BindView(R.id.btn_qq)
    ImageView mBtnQq;
    @BindView(R.id.btn_qzone)
    ImageView mBtnQzone;
    @BindView(R.id.tv_invite)
    TextView mTvInvite;
    @BindView(R.id.tv_invite_detail)
    TextView mTvInviteDetail;
    @BindView(R.id.tv_share)
    TextView mTvShare;
    @BindView(R.id.tv_share_detail)
    TextView mTvShareDetail;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    private String inviteNum = "";
    // 微信SDK
    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;

    //分享数据
    private ShareInfo mShareInfo;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite;
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
        UiUtils.startActivity(context, InviteActivity.class, bundle);
    }

    @Override
    protected void configViews() {
        title = getIntent().getStringExtra("title");
        setTitle(title);
        mPresenter.getInviteInfo();
    }

    @Override
    public void complete() {
    }

    @OnClick({R.id.btn_invite, R.id.btn_detail, R.id.btn_wx, R.id.btn_friend, R.id.btn_qq, R.id.btn_qzone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite:
            case R.id.btn_detail:
                InviteListActivity.start(this, inviteNum);
                break;
            case R.id.btn_wx:
            case R.id.btn_friend:
            case R.id.btn_qq:
            case R.id.btn_qzone:
                if (mShareInfo != null)
                    share(view.getId());
                break;
        }
    }

    /**
     * 分享
     *
     * @param resId
     */
    private void share(int resId) {

        switch (resId) {

            case R.id.btn_wx:
            case R.id.btn_friend:
                int scene = resId == R.id.btn_friend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                if (mWechatSdk == null) {
                    mWechatSdk = new WechatSdk(this, BuildConfig.APP_ID_WECHAT);
                }
                mWechatSdk.share(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, scene);

                break;
            case R.id.btn_qq:
            case R.id.btn_qzone:
                if (mQQSdk == null) {
                    mQQSdk = new QQSdk(this, BuildConfig.APP_ID_QQ);
                }
                mQQSdk.shareToQQ(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, resId == R.id.btn_qq);
                break;
        }
    }


    @Override
    public void showInviteInfo(InviteInfo inviteInfo) {
        inviteNum = inviteInfo.inviteNum;
        mShareInfo = inviteInfo.shareInfo;
        mTvInvite.setText(inviteInfo.desc);
        mTvInviteDetail.setText(inviteInfo.descLong);
        mTvShare.setText(inviteInfo.pointsDesc);
        mTvShareDetail.setText(inviteInfo.pointsDescLong);
        Glide.with(this).load(inviteInfo.laodao).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvLogo);
        mTvCompany.setText(inviteInfo.laodaoName);
        mBtnInvite.setText(UiUtils.getString(R.string.invite_num, inviteInfo.inviteNum));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQQSdk.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareChange(ShareEvent event) {
        mPresenter.shareBack("invite", 0);
    }
}
