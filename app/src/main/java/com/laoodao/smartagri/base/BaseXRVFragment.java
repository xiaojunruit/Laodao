package com.laoodao.smartagri.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.UiUtils;

import java.lang.reflect.Constructor;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/12.
 */

public abstract class BaseXRVFragment<P extends BasePresenter> extends LazyFragment<P> implements BaseAdapter.OnItemClickListener, XRecyclerView.LoadingListener, ListBaseView {


    @BindView(R.id.recyclerview)
    protected XRecyclerView mRecyclerView;

    protected BaseAdapter mAdapter;
    protected BaseHeaderView mHeader;
    protected MultiStateView mMultiStateView;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMultiStateView = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });
            mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });
        }
    }

    protected int page = 1;

    protected void initAdapter() {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);
        }
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLoadingListener(this);

            if (mHeader != null) {
                mRecyclerView.addHeaderView(mHeader.mHeaderView);
            }

            if (getHeaderLayoutId() > 0) {
                View headerView = mInflater.inflate(getHeaderLayoutId(), (ViewGroup) mRootView, false);
                mRecyclerView.addHeaderView(headerView);
            }
        }
    }

    protected void initAdapter(Class<? extends BaseAdapter> clazz) {
        mAdapter = (BaseAdapter) createInstance(clazz);
        initAdapter();
    }

    public Object createInstance(Class<?> cls) {
        Object obj;
        try {
            Constructor c1 = cls.getDeclaredConstructor(Context.class);
            c1.setAccessible(true);
            obj = c1.newInstance(mActivity);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        if (!NetworkUtils.isConnected(mApplication)) {
            UiUtils.makeText("好像没有网络哦");
            return;
        }
        page = 1;
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isConnected(mApplication)) {
            UiUtils.makeText("好像没有网络哦");
            return;
        }

        page++;
    }

    @Override
    public void complete() {
        mRecyclerView.complete();
    }

    @Override
    public void noMore(boolean noMore) {
        mRecyclerView.setIsnomore(noMore);
    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (mAdapter.getItemCount() == 0) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {
        mAdapter.addAll(items, isRefresh);
    }

    @Deprecated
    protected int getHeaderLayoutId() {
        return 0;
    }


    @Override
    protected void lazyFetchData() {

    }

    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void configViews() {

    }
}
