package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.NewsDetail;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.discovery.adapter.NewsAdapter;
import com.laoodao.smartagri.ui.discovery.contract.NewsDetailContract;
import com.laoodao.smartagri.ui.discovery.presenter.NewsDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.ig.DefaultImageGetter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailContract.NewsDetailView {


    @BindView(R.id.tv_from)
    TextView mTvFrom;
    @BindView(R.id.tv_read)
    TextView mTvRead;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.expand_text)
    TextView mExpandText;
    @BindView(R.id.btn_share_wx)
    TextView mBtnShareWx;
    @BindView(R.id.btn_share_friend)
    TextView mBtnShareFriend;
    @BindView(R.id.btn_share_qq)
    TextView mBtnShareQq;
    @BindView(R.id.btn_share_qqzone)
    TextView mBtnShareQqzone;
    @BindView(R.id.recommend_list)
    RecyclerView mRecommendList;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private int id;
    private NewsAdapter mAdapter;
    // 微信SDK
    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;
    //分享数据
    private ShareInfo mShareInfo;
    private NewsDetail mDetail;


    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, NewsDetailActivity.class, bundle);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void configViews() {
        mAdapter = new NewsAdapter(this);
        mRecommendList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecommendList.setAdapter(mAdapter);
        id = getIntent().getIntExtra("id", 0);
        mPresenter.requestNewsDetail(id);
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mPresenter.requestNewsDetail(id);
            });
            mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mPresenter.requestNewsDetail(id);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQQSdk.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void complete() {

    }

    @OnClick({R.id.btn_share_wx, R.id.btn_share_friend, R.id.btn_share_qq, R.id.btn_share_qqzone})
    public void onClick(View view) {
        if (mShareInfo != null)
            share(view.getId());
    }

    /**
     * 分享
     *
     * @param resId
     */
    private void share(int resId) {

        switch (resId) {
            case R.id.btn_share_wx:
            case R.id.btn_share_friend:
                int scene = resId == R.id.btn_share_friend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                if (mWechatSdk == null) {
                    mWechatSdk = new WechatSdk(this, BuildConfig.APP_ID_WECHAT);
                }
                mWechatSdk.share(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, scene);

                break;
            case R.id.btn_share_qq:
            case R.id.btn_share_qqzone:
                if (mQQSdk == null) {
                    mQQSdk = new QQSdk(this, BuildConfig.APP_ID_QQ);
                }
                mQQSdk.shareToQQ(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, resId == R.id.btn_share_qq);
                break;
        }
    }

    @Override
    public void newsDetailSuccess(NewsDetail detail) {
        this.mDetail = detail;
        mShareInfo = detail.shareInfo;
        mTvTitle.setText(detail.title);
        mTvTime.setText(detail.addTime);
        mTvFrom.setText(detail.source);
        RichText.fromHtml(detail.content)
                .cache(CacheType.LAYOUT)
                .borderSize(30)
                .borderColor(0xffffffff)
                .showBorder(true)
                .into(mExpandText);

        mAdapter.addAll(detail.items);
    }


    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (mDetail == null) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
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
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareChange(ShareEvent event){
        mPresenter.shareBack("news",id);
    }

}