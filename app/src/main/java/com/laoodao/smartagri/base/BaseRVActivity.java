package com.laoodao.smartagri.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.view.recyclerview.EasyRecyclerView;
import com.laoodao.smartagri.view.recyclerview.adapter.OnLoadMoreListener;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.laoodao.smartagri.view.recyclerview.swipe.OnRefreshListener;

import java.lang.reflect.Constructor;

import butterknife.BindView;

/**
 * 使用Recyclerview的activity
 *
 * @param <T>实体类-类型
 * @param <P>Presenter类型
 */
public abstract class BaseRVActivity<T, P extends BasePresenter> extends BaseActivity<P> implements RecyclerArrayAdapter.OnItemClickListener, OnLoadMoreListener, OnRefreshListener {

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
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapterWithProgress(mAdapter);
        }

    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T>> clazz, boolean refreshable, boolean loadmoreable, boolean showLine) {
        initAdapter(clazz, refreshable, loadmoreable);
        if (mRecyclerView != null && showLine) {
            mRecyclerView.setItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0);
        }

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
    public void onLoadMore() {
        if (!NetworkUtils.isConnected(mApplication)) {
            mAdapter.pauseMore();
            return;
        }
        page++;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        if (!NetworkUtils.isConnected(mApplication)) {
            mAdapter.pauseMore();
            return;
        }
    }
}
