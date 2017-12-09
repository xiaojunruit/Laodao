package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.ui.market.adapter.NewCollectionSalesAdapter;
import com.laoodao.smartagri.ui.market.contact.BrowseSalesContact;
import com.laoodao.smartagri.ui.market.presenter.BrowseSalesPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class BrowseSalesFragment extends BaseXRVFragment<BrowseSalesPresenter> implements BrowseSalesContact.BrowseSalesView {

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
        initAdapter(NewCollectionSalesAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(1));
        mRecyclerView.addItemDecoration(decoration);

    }


    @Override
    public void initData(List<Collection> collections, boolean isRefresh) {
        if (isRefresh) mAdapter.clear();
        for (int i = 0; i < collections.size(); i++) {
            mAdapter.addAll(collections.get(i).supdetail);
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestDate(1, page);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestDate(1, page);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }
}
