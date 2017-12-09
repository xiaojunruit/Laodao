package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.MarketEvent;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.market.adapter.NewCollectionSalesAdapter;
import com.laoodao.smartagri.ui.market.contact.CollectionSalesContact;
import com.laoodao.smartagri.ui.market.presenter.CollectionSalesPresenter;
import com.laoodao.smartagri.utils.ACache;
import com.laoodao.smartagri.utils.RxUtils;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class CollectionSalesFragment extends BaseXRVFragment<CollectionSalesPresenter> implements CollectionSalesContact.CollectionSalesView {
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
        mAdapter.setOnItemClickListener(position -> {
            Collection.SupdetailBean bean = (Collection.SupdetailBean) mAdapter.getItem(position);
            if (bean.isvalid) {
                SupplyDetailsActivity.start(getContext(), "供销详情", Integer.parseInt(bean.id), position);
            }
        });
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void delCollectionEvent(MarketEvent event) {
        if (event.type == Constant.SUPPLY) {
            String key = Constant.COLLECTION_SALES + page;
            ACache.get(getContext()).remove(key);
            mAdapter.remove(event.id);
            if (mAdapter.getAllData().size() == 0) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }

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
        mPresenter.requestDate(page, 1);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestDate(page, 1);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }
}
