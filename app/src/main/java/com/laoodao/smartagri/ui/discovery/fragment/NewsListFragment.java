package com.laoodao.smartagri.ui.discovery.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.NewsAdapter;
import com.laoodao.smartagri.ui.discovery.contract.NewsListContract;
import com.laoodao.smartagri.ui.discovery.presenter.NewsListPresenter;

/**
 * Created by 欧源 on 2017/4/21.
 */

public class NewsListFragment extends BaseXRVFragment<NewsListPresenter> implements NewsListContract.NewsListView {

    private int id;

    public static NewsListFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_ft_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        id = getArguments().getInt("id");
        initAdapter(NewsAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), 1);
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    protected void lazyFetchData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getNewsList(page, id, "");
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getNewsList(page, id, "");
    }
}
