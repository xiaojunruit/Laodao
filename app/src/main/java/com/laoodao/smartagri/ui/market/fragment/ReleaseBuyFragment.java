package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.SupplyMy;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.DelBuyEvent;
import com.laoodao.smartagri.event.ReleaseBuyEvent;
import com.laoodao.smartagri.ui.market.adapter.ReleaseBuyAdapter;
import com.laoodao.smartagri.ui.market.contact.ReleaseBuyContact;
import com.laoodao.smartagri.ui.market.presenter.ReleaseBuyPresenter;
import com.laoodao.smartagri.utils.ACache;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ReleaseBuyFragment extends BaseXRVFragment<ReleaseBuyPresenter> implements ReleaseBuyContact.ReleaseBuyView {
    private int itemId;

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
        mAdapter = new ReleaseBuyAdapter(mActivity, mPresenter);
        initAdapter();
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);

    }


    @Override
    public void initData(List<SupplyMy> data, boolean isRefresh) {
        if (isRefresh) mAdapter.clear();
        mAdapter.addAll(data);
    }

    @Override
    public void deleteData() {
        String key = Constant.RELEASE_BUY + page;
        ACache.get(getContext()).remove(key);
        mAdapter.remove(itemId);
        if (mAdapter.getAllData().size()==0){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void onEventMainThread(DelBuyEvent event) {
        itemId = event.itemId;
        mPresenter.requestDel(event.buyId);
    }

    @Subscribe
    public void onEventMainThread(ReleaseBuyEvent event) {
        if (event.type == 1) {
            if (mAdapter == null) {
                mAdapter = new ReleaseBuyAdapter(mActivity, mPresenter);
            }
            onRefresh();
        }

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestDate(page, 2);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestDate(page, 2);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }
}
