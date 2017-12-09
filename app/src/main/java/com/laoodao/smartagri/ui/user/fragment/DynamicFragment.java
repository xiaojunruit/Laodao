package com.laoodao.smartagri.ui.user.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ejz.xrecyclerview.XRecyclerView;
import com.flyco.tablayout.SegmentTabLayout;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.ClearMsgEvent;
import com.laoodao.smartagri.event.CloseMessageEvent;
import com.laoodao.smartagri.event.DynamicEvent;
import com.laoodao.smartagri.event.NewMessageEvent;
import com.laoodao.smartagri.ui.user.adapter.DynamicAdapter;
import com.laoodao.smartagri.ui.user.contract.DynamicContract;
import com.laoodao.smartagri.ui.user.presenter.DynamicPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public class DynamicFragment extends BaseXRVFragment<DynamicPresenter> implements DynamicContract.DynamicView {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dynamic;
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
    }

    @Override
    public void DynamicSuccess() {
        EventBus.getDefault().post(new DynamicEvent());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestDynamic(page, 3);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestDynamic(page, 3);
    }

    @Override
    protected void lazyFetchData() {
        initAdapter(DynamicAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getContext(), R.color.transparent), UiUtils.dip2px(10));
        decoration.setFistMargin(UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearMsgChange(ClearMsgEvent event) {
        if (event.tab == 2) {
            onRefresh();
        }
    }
}
