package com.laoodao.smartagri.ui.market.activity;

import android.app.Activity;
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

import com.ejz.multistateview.MultiStateView;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
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
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.MarketEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.market.contact.BuyDetailsContact;
import com.laoodao.smartagri.ui.market.presenter.BuyDetailsPresenter;
import com.laoodao.smartagri.ui.user.activity.ChatActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.adapter.ContactsAdapter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.IconButton;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/19.
 */

public class BuyDetailsActivity extends BaseActivity<BuyDetailsPresenter> implements BuyDetailsContact.BuyDetailsView {

    @BindView(R.id.ll_share)
    LinearLayout mLlShare;
    @BindView(R.id.tv_buy_name)
    TextView mTvBuyName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.tv_detailed_information)
    TextView mTvDetailedInformation;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_telephone)
    ImageView mIvTelephone;
    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    @BindView(R.id.tv_contactor)
    TextView mTvContactor;
    @BindView(R.id.tv_contacmobile)
    TextView mTvContacmobile;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.wx)
    FrameLayout mWx;
    @BindView(R.id.friend)
    FrameLayout mFriend;
    @BindView(R.id.qq)
    FrameLayout mQq;
    @BindView(R.id.qzone)
    FrameLayout mQzone;
    @BindView(R.id.id_category)
    TextView mIdCategory;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.acreage)
    TextView mAcreage;
    @BindView(R.id.ll_price)
    LinearLayout mLlPrice;
    @BindView(R.id.ll_acreage)
    LinearLayout mLlAcreage;
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.btn_friend)
    IconButton mBtnFriend;
    @BindView(R.id.btn_qq)
    IconButton mBtnQq;
    @BindView(R.id.tv_chat)
    TextView mTvChat;
    @BindView(R.id.tv_call_phone)
    TextView mTvCallPhone;

    private String title;
    private int id;
    private boolean isCollect;
    // 微信SDK
    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;
    ShareInfo mShareInfo;

    public SupplyDetail mDetail;
    private int mPosition;
    private User mUser;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_details;
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
        if ("求购详情".equals(title)) {
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
        UiUtils.startActivity(context, BuyDetailsActivity.class, bundle);
    }

    @Override
    public void initSupplyDetail(SupplyDetail detail) {
        mDetail = detail;
        mLlAmount.setVisibility("农副产品".equals(detail.category) ? View.VISIBLE : View.GONE);
        mLlAcreage.setVisibility("土地".equals(detail.category) ? View.VISIBLE : View.GONE);
        mTvBuyName.setText(detail.title);
        mTvAddress.setText(detail.areaInfo);
        mTvContent.setText(detail.content);
        mIvCollection.setSelected(detail.isCollected());
        mTvTime.setText(detail.addTime);
        mTextView.setText(UiUtils.getString(R.string.view_total, detail.viewtotal));
        mTvContactor.setText(detail.contactor);
        mTvContacmobile.setText(detail.contacmobile);
        mTvAmount.setText("0".equals(detail.amount) ? "——" : detail.amount);
        mShareInfo = detail.shareInfo;
        mIdCategory.setText(detail.category);
        mTvPrice.setText("0.00".equals(detail.price) ? "面议" : UiUtils.getString(R.string.money_symbol, detail.price));
        mAcreage.setText("0".equals(detail.acreage) ? "——" : detail.acreage);
    }

    @Override
    public void addCollection() {
        mIvCollection.setSelected(true);
    }

    @Override
    public void delCollection() {
        mIvCollection.setSelected(false);

    }

    @Override
    public void setChatGoodsInit(ChatGoodsInit data) {
        ChatActivity.start(this, mDetail.uid, mDetail.memberAvatar, mUser.avatar, Constant.SUPPLY_TAG, data, mDetail.contacmobile, mDetail.contactor);
    }

    private String imPwd = "";

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
                Toast.makeText(BuyDetailsActivity.this, "请求权限失败,请前往设置开启权限！", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQQSdk.onActivityResult(requestCode, resultCode, data);
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

    @Override
    protected void onDestroy() {
        if (mDetail != null) {
            if (!mIvCollection.isSelected()) {
                MarketEvent event = new MarketEvent();
                event.id = mPosition;
                event.type = Constant.WANT_BUY;
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
