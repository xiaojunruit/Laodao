package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.ReturnGoodsDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.OrderDetailAdapter;
import com.laoodao.smartagri.ui.user.adapter.RetDetailAdapter;
import com.laoodao.smartagri.ui.user.contract.ReturnGoodsDetailContract;
import com.laoodao.smartagri.ui.user.presenter.ReturnGoodsDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/6.
 */

public class ReturnGoodsDetailActivity extends BaseActivity<ReturnGoodsDetailPresenter> implements ReturnGoodsDetailContract.ReturnGoodsDetailView {

    @BindView(R.id.tv_sale_num)
    TextView mTvSaleNum;
    @BindView(R.id.tv_sale_total_money)
    TextView mTvSaleTotalMoney;
    @BindView(R.id.tv_sale_time)
    TextView mTvSaleTime;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_order_total_money)
    TextView mTvOrderTotalMoney;
    @BindView(R.id.tv_discount)
    TextView mTvDiscount;
    @BindView(R.id.tv_actual_money)
    TextView mTvActualMoney;
    @BindView(R.id.tv_return_goods_time)
    TextView mTvReturnGoodsTime;
    @BindView(R.id.return_goods_list)
    RecyclerView mReturnGoodsList;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private RetDetailAdapter mAdapter;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, ReturnGoodsDetailActivity.class, bundle);
    }


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return_goods_detail;
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
        mAdapter = new RetDetailAdapter(this);
        mReturnGoodsList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mReturnGoodsList.setHasFixedSize(true);
        mReturnGoodsList.setAdapter(mAdapter);
        mPresenter.requestReturnGoodsDetail(id);
    }

    @Override
    public void setReturnGoodsDetail(ReturnGoodsDetail data) {
        mTvSaleNum.setText(UiUtils.getString(R.string.sale_num, data.salesInfo.orderSn));
        mTvSaleTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.sale_total_money, data.salesInfo.orderAmount)));
        mTvSaleTime.setText(UiUtils.getString(R.string.sale_time, data.salesInfo.addTime));
        mTvOrderNum.setText(UiUtils.getString(R.string.returned_purchase, data.retInfo.retSn));
        mTvOrderTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.returned_purchase_total_money, data.retInfo.retAmount)));
        mTvDiscount.setText(Html.fromHtml(UiUtils.getString(R.string.refund_goods_exempt_money, data.retInfo.exemptMoney)));
        mTvActualMoney.setText(Html.fromHtml(UiUtils.getString(R.string.return_goods_actual_money, data.retInfo.payMoney)));
        mTvReturnGoodsTime.setText(UiUtils.getString(R.string.return_goods_time, data.retInfo.retTime));
        if (data.retDetailList != null) {
            mAdapter.addAll(data.retDetailList, true);
        }
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onEmpty() {

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
}
