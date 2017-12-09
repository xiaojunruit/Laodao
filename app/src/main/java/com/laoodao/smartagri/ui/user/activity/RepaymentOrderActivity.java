package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.RepaymentOrder;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.RepaymentOrderAdapter;
import com.laoodao.smartagri.ui.user.contract.RepaymentOrderContract;
import com.laoodao.smartagri.ui.user.presenter.RepaymentOrderPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/6.
 */

public class RepaymentOrderActivity extends BaseActivity<RepaymentOrderPresenter> implements RepaymentOrderContract.RepaymentOrderView {
    @BindView(R.id.tv_repayment_money)
    TextView mTvRepaymentMoney;
    @BindView(R.id.tv_remove)
    TextView mTvRemove;
    @BindView(R.id.tv_repayment_methods)
    TextView mTvRepaymentMethods;
    @BindView(R.id.tv_repayment_time)
    TextView mTvRepaymentTime;
    @BindView(R.id.repayment_order_list)
    RecyclerView mRepaymentOrderList;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private RepaymentOrderAdapter mAdapter;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, RepaymentOrderActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_repayment_order;
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
        mAdapter = new RepaymentOrderAdapter(this);
        mRepaymentOrderList.setLayoutManager(new LinearLayoutManager(this));
        mRepaymentOrderList.setHasFixedSize(true);
        mRepaymentOrderList.setAdapter(mAdapter);
        mPresenter.requestRepaymentOrderList(id);
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {

        }
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


    @Override
    public void complete() {

    }

    @Override
    public void setRepaymentOrder(RepaymentOrder repaymentOrder) {
        mTvRepaymentMoney.setText(Html.fromHtml(UiUtils.getString(R.string.repayment_money, repaymentOrder.money)));
        mTvRemove.setText(Html.fromHtml(UiUtils.getString(R.string.return_goods_exempt_money, repaymentOrder.exemptMoney)));
        if (TextUtils.isEmpty(repaymentOrder.accountRepaymentMoney) || TextUtils.isEmpty(repaymentOrder.cashRepaymentMoney)) {
            mTvRepaymentMethods.setVisibility(View.GONE);
        } else {
            mTvRepaymentMethods.setVisibility(View.VISIBLE);
            mTvRepaymentMethods.setText(Html.fromHtml(UiUtils.getString(R.string.repayment_mode, repaymentOrder.accountRepaymentMoney, repaymentOrder.cashRepaymentMoney)));
        }
        mTvRepaymentTime.setText(UiUtils.getString(R.string.payment_history_time, repaymentOrder.repaymentDate));
        if (repaymentOrder.repayOrder != null) {
            mAdapter.addAll(repaymentOrder.repayOrder, true);
        }
    }
}
