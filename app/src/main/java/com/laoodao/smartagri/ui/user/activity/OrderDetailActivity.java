package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.MyOrderDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.TradeRecordChangeEvent;
import com.laoodao.smartagri.ui.user.adapter.OrderDetailAdapter;
import com.laoodao.smartagri.ui.user.contract.OrderDetailContract;
import com.laoodao.smartagri.ui.user.presenter.OrderDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/29.
 */

public class OrderDetailActivity extends BaseXRVActivity<OrderDetailPresenter> implements OrderDetailContract.OrderDetailView {

    @BindView(R.id.tv_pending_payment_money)
    TextView mTvPendingPaymentMoney;
    @BindView(R.id.tv_payment)
    TextView mTvPayment;
    @BindView(R.id.fl_payment)
    FrameLayout mFlPayment;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, OrderDetailActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
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
        id = getIntent().getIntExtra("id", 0);
        mHeader = new OrderDetailHeader(this);
        initAdapter(OrderDetailAdapter.class);
        mRecyclerView.setLoadingMoreEnabled(false);
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderDetailChange(TradeRecordChangeEvent event) {
        if (id == 0) {
            return;
        }
        onRefresh();
    }

    @OnClick(R.id.tv_payment)
    public void onClick() {
        TradeRecordDetailActivity.start(this, id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestOrderDetail(id);
    }


    class OrderDetailHeader extends BaseHeaderView {

        @BindView(R.id.iv_state)
        ImageView mIvState;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.history)
        LinearLayout mHistory;
        @BindView(R.id.tv_order_num)
        TextView mTvOrderNum;
        @BindView(R.id.tv_order_total_money)
        TextView mTvOrderTotalMoney;
        @BindView(R.id.tv_actual_amount)
        TextView mTvActualAmount;
        @BindView(R.id.tv_sell_redit)
        TextView mTvSellRedit;
        @BindView(R.id.tv_sale_time)
        TextView mTvSaleTime;
        @BindView(R.id.tv_down_payments)
        TextView mTvDownPayments;
        @BindView(R.id.tv_repayments)
        TextView mTvRepayments;
        private Context mContext;

        public OrderDetailHeader(Context context) {
            super(context);
            this.mContext = context;
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_order_detail;
        }

        @OnClick(R.id.history)
        public void onClick() {
            HistoryRecordActivity.start(mContext, id);
        }
    }

    @Override
    public void orderDetailSuccess(MyOrderDetail detail) {
        mAdapter.addAll(detail.detailList, true);
        OrderDetailHeader header = ((OrderDetailHeader) mHeader);
        mFlPayment.setVisibility(detail.state == 4 ? View.VISIBLE : View.GONE);
        mTvPendingPaymentMoney.setText(Html.fromHtml(UiUtils.getString(R.string.pending_pay, detail.orderAmount)));
        switch (detail.state) {
            case 1://欠款
                header.mIvState.setBackgroundResource(R.mipmap.bg_no_settle);
                header.mTvState.setText("欠款");
                break;
            case 2://还款中
                header.mIvState.setBackgroundResource(R.mipmap.bg_in_settle);
                header.mTvState.setText("还款中");
                break;
            case 3://已完成
                header.mIvState.setBackgroundResource(R.mipmap.bg_su_settle);
                header.mTvState.setText("已完成");
                break;
            case 4://未付
                header.mIvState.setBackgroundResource(R.mipmap.bg_in_settle);
                header.mTvState.setText("未付");
                break;
            case 5://已付
                header.mIvState.setBackgroundResource(R.mipmap.bg_su_settle);
                header.mTvState.setText("已付");
                break;
        }
        header.mTvTime.setText(detail.repayList.time);
        header.mTvTitle.setText(detail.repayList.desc);
        header.mTvSaleTime.setText(UiUtils.getString(R.string.sale_time, detail.addTime));
        header.mTvOrderNum.setText(UiUtils.getString(R.string.order_num, detail.orderSn));
        header.mTvOrderTotalMoney.setVisibility(TextUtils.isEmpty(detail.orderAmount) ? View.GONE : View.VISIBLE);
        header.mTvOrderTotalMoney.setText(UiUtils.getString(R.string.order_total_money, detail.orderAmount));
        header.mTvDownPayments.setText(Html.fromHtml(UiUtils.getString(R.string.actual_money, detail.payAmount)));
        header.mTvActualAmount.setText(Html.fromHtml(UiUtils.getString(detail.userType == 1 ? R.string.repayment : R.string.unpaid, detail.noPayAmount)));
        header.mTvSellRedit.setText(Html.fromHtml(UiUtils.getString(R.string.actual_money, detail.payAmount)));
        header.mTvRepayments.setText(Html.fromHtml(UiUtils.getString(R.string.repayments_money, detail.haveRepayment)));
        if (detail.userType == 1) {
            header.mTvRepayments.setVisibility(View.VISIBLE);
            header.mTvSellRedit.setVisibility(View.GONE);
            header.mTvDownPayments.setVisibility(View.VISIBLE);
        } else {
            header.mTvRepayments.setVisibility(View.GONE);
            header.mTvSellRedit.setVisibility(View.VISIBLE);
            header.mTvDownPayments.setVisibility(View.GONE);
        }


    }
}
