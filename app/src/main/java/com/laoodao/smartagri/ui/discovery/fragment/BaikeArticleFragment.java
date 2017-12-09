package com.laoodao.smartagri.ui.discovery.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CategoryEvent;
import com.laoodao.smartagri.ui.discovery.activity.CropDetailActivity;
import com.laoodao.smartagri.ui.discovery.adapter.BaikeAdapter;
import com.laoodao.smartagri.ui.discovery.contract.BaikeArticleContract;
import com.laoodao.smartagri.ui.discovery.presenter.BaikeArticlePresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by WORK on 2017/4/18.
 */

public class BaikeArticleFragment extends BaseXRVFragment<BaikeArticlePresenter> implements BaikeArticleContract.BaikeArticleView {
    private int mCropId = 0;
    private int mCategoryId = 0;
    private boolean mIsFirst = false;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_baike_article;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public static BaikeArticleFragment newInstance(int cropId, int categoryId, boolean isFirst) {
        Bundle args = new Bundle();
        args.putInt("cropId", cropId);
        args.putInt("categoryId", categoryId);
        args.putBoolean("isFirst", isFirst);
        BaikeArticleFragment fragment = new BaikeArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void setCategoryId(int categoryId) {
        if (mCategoryId != categoryId) {
            mCategoryId = categoryId;
            if (mMultiStateView != null) {
                mMultiStateView.post(() -> {
                    mAdapter.clear();
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    onRefresh();
                });
            }

        }

    }


    @Override
    public void configViews() {
        mIsFirst = getArguments().getBoolean("isFirst");
        mCropId = getArguments().getInt("cropId");
        if (mIsFirst) {
            mCategoryId = getArguments().getInt("categoryId");
        }
        initAdapter(BaikeAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity,
                R.color.common_divider_narrow), 1, 45, 0);
        decoration.setFistMargin(UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(position -> {
            CropDetailActivity.start(getContext(),((Baike)mAdapter.getItem(position)).id);
        });
    }

    @Override
    public void showBaike(Baike baike) {
        mAdapter.addAll(baike);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestBaike(page, mCropId, mCategoryId);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestBaike(page, mCropId, mCategoryId);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (mIsFirst) {
            onRefresh();
        }

    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }


    @Subscribe
    public void onEventMainThread(CategoryEvent event) {
        if (getUserVisibleHint()) {
            mCategoryId = event.id;
            mAdapter.clear();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            onRefresh();
        }
    }
}
