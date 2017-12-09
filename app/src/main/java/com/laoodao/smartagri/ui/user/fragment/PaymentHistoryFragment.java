package com.laoodao.smartagri.ui.user.fragment;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.PaymentHistoryAdapter;
import com.laoodao.smartagri.ui.user.contract.PaymentHistoryContract;
import com.laoodao.smartagri.ui.user.presenter.PaymentHistoryPresenter;
import com.laoodao.smartagri.utils.LogUtil;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/28.
 */

public class PaymentHistoryFragment extends BaseXRVFragment<PaymentHistoryPresenter> implements PaymentHistoryContract.PaymentHistoryView {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_payment_history;
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
        initAdapter(PaymentHistoryAdapter.class);
        onRefresh();
    }


    @Override
    public void complete() {

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestPaymentHistory(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestPaymentHistory(page);
    }

    @Override
    public void commit() {
        mRecyclerView.refreshComplete();
    }
}
