package com.laoodao.smartagri.ui.user.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.SupplyAdapter;
import com.laoodao.smartagri.ui.user.contract.SupplyCollectionContract;
import com.laoodao.smartagri.ui.user.presenter.SupplyCollectionPresenter;
import com.laoodao.smartagri.utils.UiUtils;

/**
 * Created by WORK on 2017/4/18.
 */

public class SupplyCollectionFragment extends BaseXRVFragment<SupplyCollectionPresenter> implements SupplyCollectionContract.BuyView {
    private int mMemberId, mType;

    public static SupplyCollectionFragment newInstance(int memberId, int type) {
        Bundle args = new Bundle();
        args.putInt("memberId", memberId);
        args.putInt("type", type);
        SupplyCollectionFragment fragment = new SupplyCollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_ft_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    public void configViews() {
        Bundle args = getArguments();
        mMemberId = args.getInt("memberId");
        mType = args.getInt("type");
        mAdapter = new SupplyAdapter(getContext(), mType);
        initAdapter();
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getContext(), R.color.background), UiUtils.dip2px(10), 0, 0));

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestList(mMemberId, mType, page);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestList(mMemberId, mType, page);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }
}