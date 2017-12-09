package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.ui.market.adapter.NewCollectionBuyAdapter;
import com.laoodao.smartagri.ui.market.contact.BrowseBuyContact;
import com.laoodao.smartagri.ui.market.presenter.BrowseBuyPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class BrowseBuyFragment extends BaseXRVFragment<BrowseBuyPresenter> implements BrowseBuyContact.BrowseBuyView {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_release_buy;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder().
                appComponent(appComponent).
                build().
                inject(this);
    }

    @Override
    public void configViews() {
        initAdapter(NewCollectionBuyAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(1));
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void initData(List<Collection> list, boolean isRefresh) {
        if (isRefresh) mAdapter.clear();
        for (int i = 0; i < list.size(); i++) {
            mAdapter.addAll(list.get(i).supdetail);
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestData(page, 2);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestData(page, 2);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }
}
