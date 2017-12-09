package com.laoodao.smartagri.ui.user.fragment;

import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.ViewGroupHierarchyChildViewAddEvent;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.PendingConfirmOrderAdapter;
import com.laoodao.smartagri.ui.user.contract.PendingConfirmOrderContract;
import com.laoodao.smartagri.ui.user.presenter.PendingConfirmOrderPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/5.
 */

public class PendingConfirmOrderFragment extends BaseXRVFragment<PendingConfirmOrderPresenter> implements PendingConfirmOrderContract.PendingConfirmOrderView {

    @BindView(R.id.tv_actual_total_money)
    TextView mTvActualTotalMoney;
    @BindView(R.id.cb_all)
    CheckBox mCbAll;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.settlement)
    RelativeLayout mSettlement;
    private double actualTotalMoney;

    @Override
    public void pendingConfirmOrderSuccess(List<MyOrder> myOrders) {
        mSettlement.setVisibility(myOrders.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_pending_confirm_order;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        initAdapter(PendingConfirmOrderAdapter.class);
        mTvActualTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.actual_money, formatDouble(actualTotalMoney))));
        listener();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestPendingConfirmOrder(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestPendingConfirmOrder(page);
    }

    private void listener() {
        ((PendingConfirmOrderAdapter) mAdapter).setOnCheckClick((actualMoeny, isCheck, su, un) -> {
            if (su == mAdapter.getItemCount()) {
                mCbAll.setChecked(true);
            } else if (un <= mAdapter.getItemCount()) {
                mCbAll.setChecked(false);
            }
            if (isCheck) {
                actualTotalMoney += actualMoeny;
            } else {
                actualTotalMoney -= actualMoeny;
            }
            mTvSubmit.setEnabled(su > 0);
            mTvActualTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.actual_money, formatDouble(actualTotalMoney))));
        });
    }

    private void isSate() {
        actualTotalMoney = 0;
        if (mCbAll.isChecked()) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                MyOrder myOrder = (MyOrder) mAdapter.getAllData().get(i);
                actualTotalMoney += Double.parseDouble(myOrder.payAmount);
            }
        }
        mTvSubmit.setEnabled(mCbAll.isChecked());
        ((PendingConfirmOrderAdapter) mAdapter).setCbState(mCbAll.isChecked());
        mTvActualTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.actual_money, formatDouble(actualTotalMoney))));
        mAdapter.notifyDataSetChanged();
    }


    public static String formatDouble(double d) {
        if (d < 0.1) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    @OnClick({R.id.cb_all, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_all:
                isSate();
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
