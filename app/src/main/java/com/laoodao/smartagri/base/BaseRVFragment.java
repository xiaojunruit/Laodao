package com.laoodao.smartagri.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.view.recyclerview.EasyRecyclerView;
import com.laoodao.smartagri.view.recyclerview.adapter.OnLoadMoreListener;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.laoodao.smartagri.view.recyclerview.swipe.OnRefreshListener;

import java.lang.reflect.Constructor;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/12.
 */

public abstract class BaseRVFragment<T, P extends BasePresenter> extends BaseFragment<P> implements RecyclerArrayAdapter.OnItemClickListener, OnLoadMoreListener, OnRefreshListener {


    @BindView(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;

    protected RecyclerArrayAdapter<T> mAdapter;


    protected int page = 1;

    private void initAdapter(boolean refreshable, boolean loadmoreable) {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);

            mAdapter.setError(R.layout._widget_loadmore_error_view).setOnClickListener(v -> mAdapter.resumeMore());
            if (loadmoreable) {
                mAdapter.setMore(R.layout._widget_loadmore_view, this);
                mAdapter.setNoMore(R.layout._widget_nomore_view);
            }
            if (refreshable && mRecyclerView != null) {
                mRecyclerView.setRefreshListener(this);
            }
        }

        if (mRecyclerView != null) {
            mRecyclerView.setRefreshingColorResources(R.color.colorAccent);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//            mRecyclerView.setItemDecoration(ContextCompat.getColor(mActivity, R.color.common_divider_narrow), 1, 0, 0);
            mRecyclerView.setAdapterWithProgress(mAdapter);
        }

    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter<T>) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
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

    // 说明缓存也没有加载，那就显示errorview，如果有缓存，即使刷新失败也不显示error
    protected void loaddingError() {
        if (mAdapter.getCount() < 1) {
            mAdapter.clear();
        }
        mAdapter.pauseMore();
        mRecyclerView.setRefreshing(false);
        mRecyclerView.showTipViewAndDelayClose("好像没有网络哦");
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

}
