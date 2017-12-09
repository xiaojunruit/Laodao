package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.PaymentDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.PaymentDetailAdapter;
import com.laoodao.smartagri.ui.user.contract.PaymentDetailContract;
import com.laoodao.smartagri.ui.user.presenter.PaymentDetailPresenter;

import java.util.List;

/**
 * Created by WORK on 2017/5/2.
 */

public class PaymentDetailActivity extends BaseXRVActivity<PaymentDetailPresenter> implements PaymentDetailContract.PaymentDetailView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_detail;
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
        mHeader = new PaymentDetailHeader(this);
        initAdapter(PaymentDetailAdapter.class);
        mPresenter.requestPaymentDetail(1);
    }

    @Override
    public void paymentDetailSuccess(List<PaymentDetail> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(list);
    }

    class PaymentDetailHeader extends BaseHeaderView {

        public PaymentDetailHeader(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_payment_detail;
        }
    }

}
