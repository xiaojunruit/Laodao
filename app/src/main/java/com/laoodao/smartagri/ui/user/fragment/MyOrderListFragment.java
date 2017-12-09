package com.laoodao.smartagri.ui.user.fragment;

import android.os.Bundle;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.MyOrderAdapter;
import com.laoodao.smartagri.ui.user.contract.MyOrderListContract;
import com.laoodao.smartagri.ui.user.presenter.MyOrderListPresenter;

/**
 * Created by WORK on 2017/5/2.
 */

public class MyOrderListFragment extends BaseXRVFragment<MyOrderListPresenter> implements MyOrderListContract.MyOrderListView {

    private int type;

    @Override
    public int getLayoutResId() {
        return R.layout.common_ft_recyclerview;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static MyOrderListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MyOrderListFragment fragment = new MyOrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void configViews() {
        type = getArguments().getInt("type");
        initAdapter(MyOrderAdapter.class);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestMyOrder(page, type);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestMyOrder(page, type);
    }

}
