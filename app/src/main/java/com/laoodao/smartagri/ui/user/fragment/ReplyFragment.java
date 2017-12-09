package com.laoodao.smartagri.ui.user.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ejz.xrecyclerview.XRecyclerView;
import com.flyco.tablayout.SegmentTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.ClearMsgEvent;
import com.laoodao.smartagri.event.CloseMessageEvent;
import com.laoodao.smartagri.event.NewMessageEvent;
import com.laoodao.smartagri.event.ReplyEvent;
import com.laoodao.smartagri.ui.user.adapter.ReplyAdapter;
import com.laoodao.smartagri.ui.user.contract.ReplyContract;
import com.laoodao.smartagri.ui.user.presenter.ReplyPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public class ReplyFragment extends BaseXRVFragment<ReplyPresenter> implements ReplyContract.ReplyView {
    private boolean notice;
    private boolean dynamic;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_reply;
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
    public void replySuucess() {
        EventBus.getDefault().post(new ReplyEvent());
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestReply(page, 2);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestReply(page, 2);
    }

    @Override
    protected void lazyFetchData() {
        initAdapter(ReplyAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getContext(), R.color.transparent), UiUtils.dip2px(10));
        decoration.setFistMargin(UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void messageChange(NewMessageEvent event) {
        notice = event.notice;
        dynamic = event.dynamic;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearMsgChange(ClearMsgEvent event) {
        if (event.tab == 1) {
            onRefresh();
        }
    }
}
