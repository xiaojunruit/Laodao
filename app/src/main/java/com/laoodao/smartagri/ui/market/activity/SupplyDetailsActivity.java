package com.laoodao.smartagri.ui.market.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ejz.multistateview.MultiStateView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.ChatGoodsInit;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.bean.SupplyDetail;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.MarketEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.market.contact.SupplyDetailsContact;
import com.laoodao.smartagri.ui.market.presenter.SupplyDetailsPresenter;
import com.laoodao.smartagri.ui.qa.adapter.ImagePreviewActivity;
import com.laoodao.smartagri.ui.user.activity.ChatActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.IconButton;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SupplyDetailsActivity extends BaseActivity<SupplyDetailsPresenter> implements SupplyDetailsContact.SupplyDetailsView {
    @BindView(R.id.ll_share)
    LinearLayout mLlShare;
    @BindView(R.id.tv_buy_name)
    TextView mTvBuyName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.tv_detailed_information)
    TextView mTvDetailedInformation;
    @BindView(R.id.btn_friend)
    IconButton mBtnFriend;
    @BindView(R.id.btn_qq)
    IconButton mBtnQq;
    @BindView(R.id.btn_qzone)
    IconButton mBtnQzone;
    @BindView(R.id.iv_telephone)
    ImageView mIvTelephone;
    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_category)
    TextView mTvCategory;
    @BindView(R.id.tv_contactor)
    TextView mTvContactor;
    @BindView(R.id.tv_contacmobile)
    TextView mTvContacmobile;
    @BindView(R.id.wx)
    FrameLayout mWx;
    @BindView(R.id.friend)
    FrameLayout mFriend;
    @BindView(R.id.qq)
    FrameLayout mQq;
    @BindView(R.id.qzone)
    FrameLayout mQzone;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.acreage)
    TextView mAcreage;
    @BindView(R.id.ll_acreage)
    LinearLayout mLlAcreage;
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    @BindView(R.id.banner_img)
    Banner mBannerImg;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    public SupplyDetail mDetail;
    private User mUser;


    private String title;
    private int id;
    private int mPosition;
    // 微信SDK
    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;
    ShareInfo mShareInfo;


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_supply_details;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        mPosition = getIntent().getIntExtra("position", -1);
        setTitle(title);
        if ("供销详情".equals(title)) {
            mLlShare.setVisibility(View.VISIBLE);
            mIvCollection.setVisibility(View.VISIBLE);
            mIvTelephone.setVisibility(View.VISIBLE);
        } else {
            mLlShare.setVisibility(View.GONE);
            mIvCollection.setVisibility(View.GONE);
            mIvTelephone.setVisibility(View.GONE);
        }
        mPresenter.requestSupplyDetail(id);

        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.requestSupplyDetail(id);
        });
        mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.requestSupplyDetail(id);
        });

    }

    public static void start(Context context, String title, int id) {
        start(context, title, id, -1);
    }

    public static void start(Context context, String title, int id, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("id", id);
        bundle.putInt("position", position);
        UiUtils.startActivity(context, SupplyDetailsActivity.class, bundle);
    }

    @Override
    public void initSupplyDetail(SupplyDetail detail) {
        mDetail = detail;
        mLlAmount.setVisibility("农副产品".equals(detail.category) ? View.VISIBLE : View.GONE);
        mLlAcreage.setVisibility("土地".equals(detail.category) ? View.VISIBLE : View.GONE);
        mTvBuyName.setText(detail.title);
        mContent.setText(detail.content);
        mTvTime.setText(UiUtils.getString(R.string.release_time, detail.addTime));
        mTvTime.setVisibility(detail.addTime.isEmpty() ? View.GONE : View.VISIBLE);
        mTvPrice.setText(Double.parseDouble(detail.price) <= 0 ? "面议" : UiUtils.getString(R.string.money_symbol, detail.price));
        mTextView.setText(UiUtils.getString(R.string.view_total, detail.viewtotal));
        mTvAddress.setText(detail.areaInfo);
        mTvCategory.setText(detail.category);
        mTvContactor.setText(detail.contactor);
        mTvContacmobile.setText(detail.contacmobile);
        mTvAmount.setText("0".equals(detail.amount) ? "——" : detail.amount);

        mIvCollection.setSelected(detail.isCollected());

        mAcreage.setText("0".equals(detail.acreage) ? "——" : detail.acreage);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < detail.thumb.length; i++) {
            images.add(detail.thumb[i]);
        }
        if (detail.thumb.length != 0) {
            mBannerImg.setVisibility(View.VISIBLE);
            mBannerImg.isAutoPlay(false);
            mBannerImg.setBannerStyle(BannerConfig.NUM_INDICATOR);
            mBannerImg.setImageLoader(new GlideImageLoader()).setImages(images).start();
        } else {
            mBannerImg.setVisibility(View.GONE);
        }

        mBannerImg.setOnBannerListener(position -> {
            if (images.size() != 0) {
                ImagePreviewActivity.start(this, images, position);
            }
        });

        mShareInfo = detail.shareInfo;

    }

    @Override
    public void setChatGoodsInit(ChatGoodsInit data) {
        ChatActivity.start(this, mDetail.uid, mDetail.memberAvatar, mUser.avatar, Constant.SUPPLY_TAG, data, mDetail.contacmobile,mDetail.contactor);
    }

    @Override
    public void addCollection() {
        mIvCollection.setSelected(true);
    }

    @Override
    public void delCollection() {
        mIvCollection.setSelected(false);
    }

    private String imPwd="";

    private void loginHuanXin() {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            Toast.makeText(this, "正在连接...", Toast.LENGTH_SHORT).show();
            User info = Global.getInstance().getUserInfo();
            if (!TextUtils.isEmpty(info.emcode)) {
                imPwd = info.emcode;
            } else {
                String pwd = SharedPreferencesUtil.getInstance().getString(Constant.HUAN_XIN_PWD, "");
                if (!TextUtils.isEmpty(pwd)) {
                    imPwd = pwd;
                }
            }
            if (TextUtils.isEmpty(imPwd)) {
                Toast.makeText(this, "密码获取失败，请重新获取密码", Toast.LENGTH_SHORT).show();
                return;
            }
            EMClient.getInstance().login(info.uid, imPwd, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    mPresenter.chatInit(mDetail.id, Constant.SUPPLY_TAG);
                    Log.e("main", "登录聊天服务器成功！");
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    Log.e("main", "登录聊天服务器失败！code=" + code + "----" + message);
                }
            });
        } else {
            mPresenter.chatInit(mDetail.id, Constant.SUPPLY_TAG);
        }
    }

    private void requestPermission() {
        PermissionUtil.recordAudio(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {
                loginHuanXin();
            }

            @Override
            public void failure() {
                Toast.makeText(SupplyDetailsActivity.this, "请求权限失败,请前往设置开启权限！", Toast.LENGTH_SHORT).show();
            }
        }, new RxPermissions(this));
    }

    @OnClick({R.id.iv_telephone, R.id.iv_collection, R.id.wx, R.id.friend, R.id.qq, R.id.qzone, R.id.tv_chat, R.id.tv_call_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_chat:
                if (!Global.getInstance().isLoggedIn()) {
                    UiUtils.startActivity(LoginActivity.class);
                    return;
                }
                if (mUser == null) {
                    mUser = Global.getInstance().getUserInfo();
                }
                requestPermission();
                break;
            case R.id.tv_call_phone:
            case R.id.iv_telephone:
                mPresenter.addTellog(id);
                DeviceUtils.openDial(getApplication().getApplicationContext(), mTvContacmobile.getText().toString());
                break;
            case R.id.iv_collection:
                if (mIvCollection.isSelected()) {
                    mPresenter.requestDel(id);
                } else {
                    mPresenter.requestAdd(id);
                }
                break;
            case R.id.wx:
            case R.id.friend:
            case R.id.qq:
            case R.id.qzone:
                if (mShareInfo != null) share(view.getId());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQQSdk.onActivityResult(requestCode, resultCode, data);
    }

    private void share(int resId) {
        switch (resId) {
            case R.id.wx:
            case R.id.friend:
                int scene = resId == R.id.friend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                if (mWechatSdk == null) {
                    mWechatSdk = new WechatSdk(getApplicationContext(), BuildConfig.APP_ID_WECHAT);
                }
                mWechatSdk.share(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, scene);
                break;
            case R.id.qq:
            case R.id.qzone:
                if (mQQSdk == null) {
                    mQQSdk = new QQSdk(this, BuildConfig.APP_ID_QQ);
                }
                mQQSdk.shareToQQ(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, resId == R.id.qq);
                break;
        }

    }

    @OnClick(R.id.banner_img)
    public void onClick() {
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

    }


    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).dontAnimate()
                    .error(R.mipmap.bg_big_seize_seat)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        if (mDetail != null) {
            if (!mIvCollection.isSelected()) {
                MarketEvent event = new MarketEvent();
                event.id = mPosition;
                event.type = Constant.SUPPLY;
                EventBus.getDefault().post(event);
            }
        }
        super.onDestroy();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareChange(ShareEvent event) {
        mPresenter.shareBack("supply", id);
    }
}


