package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundLinearLayout;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.CottonfieldComment;
import com.laoodao.smartagri.bean.CottonfieldDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.discovery.adapter.PriceDetailAdapter;
import com.laoodao.smartagri.ui.discovery.contract.PriceDetailsContract;
import com.laoodao.smartagri.ui.discovery.dialog.SharePopup;
import com.laoodao.smartagri.ui.discovery.presenter.PriceDetailsPresenter;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.dialog.ReplyDialog;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.StatisticalChartView;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PriceDetailsActivity extends BaseXRVActivity<PriceDetailsPresenter> implements PriceDetailsContract.PriceDetailsView, View.OnClickListener {


    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.rll_reply)
    RoundLinearLayout mRllReply;
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    private ReplyDialog mReplyDialog;

    private SharePopup mPopup;
    private ShareDialog shareDialog;
    private int mId;
    private int parentId;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, PriceDetailsActivity.class, bundle);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_details;
    }

    @Override
    protected void configViews() {
        mId = getIntent().getIntExtra("id", 0);
        mHeader = new PriceDetailHeader(this);
        initAdapter(PriceDetailAdapter.class);
        mReplyDialog = new ReplyDialog(this);
        mReplyDialog.setCallBack(reply -> {
            mPresenter.addComment(mId, reply);
        });
        shareDialog = new ShareDialog(this);
        onRefresh();

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestList(mId);
        mPresenter.CommentList(mId, page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.CommentList(mId, page);
    }

    @OnClick({R.id.iv_return, R.id.iv_more, R.id.rll_reply, R.id.iv_collect, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_collect:
            case R.id.iv_share:
            case R.id.rll_reply:
                boolean loggedIn = Global.getInstance().isLoggedIn();
                if (!loggedIn) {
                    UiUtils.startActivity(LoginActivity.class);
                    return;
                }
                break;
        }

        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_more:
                mPopup.anchorView(ivMore);
                mPopup.show();
                break;
            case R.id.tv_collect:
                mPresenter.requestFollow(parentId);
                break;
            case R.id.tv_share:
                shareDialog.show();
                break;
            case R.id.rll_reply:
                mReplyDialog.show();
                break;
            case R.id.iv_collect:
                mPresenter.requestFollow(parentId);
                break;
            case R.id.iv_share:
                shareDialog.show();
                break;
        }
    }

    @Override
    public void successList(CottonfieldDetail detail) {
//        Fragment fragment[] = new Fragment[]{SevenChartFragment.newInstance(detail.week.yaxis, detail.week.xaxis, detail.week.series, "aaaa")
//                , SevenChartFragment.newInstance(detail.month.yaxis, detail.month.xaxis, detail.month.series, "aaaa")};
        PriceDetailHeader header = (PriceDetailHeader) mHeader;
        parentId = detail.detail.id;
        Glide.with(this).load(detail.detail.cover).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.bg_seize_seat).error(R.mipmap.bg_seize_seat).into(header.mRivImg);
        header.tvType.setText(detail.detail.name);
        header.tvPrice.setText(Html.fromHtml(getResources().getString(R.string.new_price_blue, detail.detail.price)));
        header.mTvDesc.setText(detail.detail.desc);
        header.tvAdress.setText(detail.detail.areaName);
        shareDialog.setShareInfo(detail.share);
        header.mWeekView.setData(detail.week.yaxis, detail.week.xaxis, detail.week.series, detail.week.title);
        header.mMonthView.setData(detail.month.yaxis, detail.month.xaxis, detail.month.series, detail.month.title);
        mIvCollect.setSelected(detail.detail.isWonder == 1);
        mPopup = new SharePopup(this, this, detail.detail.isWonder);

    }

    @Override
    public void successCommentList(List<CottonfieldComment> comments, boolean isRefresh) {
        PriceDetailHeader header = (PriceDetailHeader) mHeader;
        if (comments.size() == 0) {
            header.noReply.setVisibility(View.VISIBLE);
        } else {
            header.noReply.setVisibility(View.GONE);
        }
        mAdapter.addAll(comments, isRefresh);
    }

    @Override
    public void successAddComment(CottonfieldComment comment) {
        mAdapter.insert(comment, 0);
        PriceDetailHeader header = (PriceDetailHeader) mHeader;
        header.noReply.setVisibility(View.GONE);

    }

    @Override
    public void successFollow() {
        mIvCollect.setSelected(!mIvCollect.isSelected());
    }


    class PriceDetailHeader extends BaseHeaderView {
        @BindView(R.id.tv_nearly_seven_days)
        TextView tvNearlySevenDays;
        @BindView(R.id.tv_this_month)
        TextView tvThisMonth;
        @BindView(R.id.no_reply)
        TextView noReply;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_adress)
        TextView tvAdress;
        @BindView(R.id.riv_img)
        RoundedImageView mRivImg;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.weekView)
        StatisticalChartView mWeekView;
        @BindView(R.id.monthView)
        StatisticalChartView mMonthView;

        public PriceDetailHeader(Context context) {
            super(context);
            tvNearlySevenDays.setSelected(true);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_price_datails;
        }

        @OnClick({R.id.tv_nearly_seven_days, R.id.tv_this_month, R.id.no_reply})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_nearly_seven_days:
                    tvNearlySevenDays.setSelected(true);
                    tvThisMonth.setSelected(false);
                    mWeekView.setVisibility(View.VISIBLE);
                    mMonthView.setVisibility(View.GONE);
                    break;
                case R.id.tv_this_month:
                    tvNearlySevenDays.setSelected(false);
                    tvThisMonth.setSelected(true);
                    mWeekView.setVisibility(View.GONE);
                    mMonthView.setVisibility(View.VISIBLE);
                    break;
                case R.id.no_reply:
                    mReplyDialog.show();
                    break;

            }
        }

    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareChange(ShareEvent event) {
        mPresenter.shareBack("price", mId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shareDialog.onActivityResult(requestCode, resultCode, data);
    }
}