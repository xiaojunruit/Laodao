package com.laoodao.smartagri.ui.user.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseRVFragment;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.TradeRecordChangeEvent;
import com.laoodao.smartagri.ui.user.adapter.TradeRecordAdapter;
import com.laoodao.smartagri.ui.user.contract.TradeRecordContract;
import com.laoodao.smartagri.ui.user.presenter.TradeRecordPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 */

public class TradeRecordFragment extends BaseXRVFragment<TradeRecordPresenter> implements TradeRecordContract.TradeRecordView {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_record;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        initAdapter(TradeRecordAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), 0);
        decoration.setFistMargin(UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);

    }



    @Override
    protected void lazyFetchData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestTransactionRecord(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestTransactionRecord(page);
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tradeRecordChange(TradeRecordChangeEvent event) {
        if (mAdapter==null){
            return;
        }
        onRefresh();
    }


    @Override
    public void tradeRecordSuccess(List<TradeRecord> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(list);
    }
}
