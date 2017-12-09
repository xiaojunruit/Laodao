package com.laoodao.smartagri.ui.user.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ejz.xrecyclerview.XRecyclerView;
import com.flyco.tablayout.SegmentTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.ClearMsgEvent;
import com.laoodao.smartagri.event.CloseMessageEvent;
import com.laoodao.smartagri.event.CloseNoticeEvent;
import com.laoodao.smartagri.event.NewMessageEvent;
import com.laoodao.smartagri.event.ReadMsgEvent;
import com.laoodao.smartagri.event.RefreshMsgEvent;
import com.laoodao.smartagri.ui.user.activity.MessageDetailActivity;
import com.laoodao.smartagri.ui.user.adapter.NoticeAdapter;
import com.laoodao.smartagri.ui.user.contract.NoticeContract;
import com.laoodao.smartagri.ui.user.presenter.NoticePresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public class NoticeFragment extends BaseXRVFragment<NoticePresenter> implements NoticeContract.NoticeView {
    private boolean dynamic;
    private boolean answer;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_notice;
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
    public void noticeSuucess(List<Notice> notices, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(notices);
    }

    @Override
    public void isNewMessage(NewMessage message) {
        EventBus.getDefault().post(new RefreshMsgEvent(message));
    }

    @Override
    public void readMsgSucess(String num) {
        if ("0.0".equals(num)) {
            EventBus.getDefault().post(new CloseNoticeEvent());
        }
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestNotice(page, 1);
        mPresenter.requestMessage();

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestNotice(page, 1);
    }

    @Override
    protected void lazyFetchData() {
        initAdapter(NoticeAdapter.class);
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
    public void readMsgChange(ReadMsgEvent event) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mPresenter.readMsg(event.id);
            }
        });

    }


    @Subscribe
    public void messageChange(NewMessageEvent event) {
        answer = event.answer;
        dynamic = event.dynamic;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearMsgChange(ClearMsgEvent event) {
        if (event.tab == 0) {
            onRefresh();
        }
    }


}
