package com.laoodao.smartagri.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.utils.LogUtil;

import java.lang.reflect.Constructor;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/12.
 */

public abstract class BaseXRVActivity<P extends BasePresenter> extends BaseActivity<P> implements BaseAdapter.OnItemClickListener, XRecyclerView.LoadingListener, ListBaseView {


    @BindView(R.id.recyclerview)
    protected XRecyclerView mRecyclerView;
    protected BaseAdapter mAdapter;
    protected BaseHeaderView mHeader;

    protected MultiStateView mMultiStateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
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
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLoadingListener(this);
//            XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 20);
//            mRecyclerView.addItemDecoration(decoration);
//            if (getHeaderLayoutId() > 0) {
//                View headerView = LayoutInflater.from(this).inflate(getHeaderLayoutId(), null, false);
//                mRecyclerView.addHeaderView(headerView);
//                onCreateHeaderView(headerView);
//            }
            if (mHeader != null) {
                mRecyclerView.addHeaderView(mHeader.mHeaderView);
            }

        }
    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (mAdapter.getItemCount() == 0) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }
    }

    protected void onCreateHeaderView(View headerView) {

    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

        mAdapter.addAll(items, isRefresh);

    }

    protected void initAdapter(Class<? extends BaseAdapter> clazz, BaseHeaderView headerView) {
        this.mHeader = headerView;
        initAdapter(clazz);
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
            obj = c1.newInstance(this);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
        page = 1;
    }

    @Override
    public void onLoadMore() {
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

    protected int getHeaderLayoutId() {
        return 0;
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
}
