package com.laoodao.smartagri.ui.user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.AskWonderList;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.AskWonderAdapter;
import com.laoodao.smartagri.ui.user.contract.AskCollectionContract;
import com.laoodao.smartagri.ui.user.presenter.AskCollectionPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 2017/4/18.
 */

public class AskCollectionFragment extends BaseXRVFragment<AskCollectionPresenter> implements AskCollectionContract.AskCollectionView {
    private int mMemberId;

    @Override
    public int getLayoutResId() {
        return R.layout.common_ft_recyclerview;
    }


    public static AskCollectionFragment newInstance(int memberId) {
        Bundle args = new Bundle();
        args.putInt("memberId", memberId);
        AskCollectionFragment fragment = new AskCollectionFragment();
        fragment.setArguments(args);
        return fragment;
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
        initAdapter(AskWonderAdapter.class);
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getContext(), R.color.background), UiUtils.dip2px(10), 0, 0));
        ((AskWonderAdapter) mAdapter).setOnFollowClickListener(((isCheck, position,id) -> {
            if (isCheck){
                mPresenter.unfollow(id,position);
            }else{
                mPresenter.follow(id,position);
            }
        }));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestList(mMemberId, page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestList(mMemberId, page);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }

    @Override
    public void followSuccess(int dataPosition, Map<String, String> data) {
        AskWonderList askData = ((AskWonderAdapter) mAdapter).getItem(dataPosition);
        askData.knowTotal = data.get("member_names_total");
        askData.isWonder = 1;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void unFollowSuccess(int dataPosition, Map<String, String> data) {
        AskWonderList askData = ((AskWonderAdapter) mAdapter).getItem(dataPosition);
        askData.knowTotal = data.get("member_names_total");
        askData.isWonder = 0;
        mAdapter.notifyDataSetChanged();
    }
}