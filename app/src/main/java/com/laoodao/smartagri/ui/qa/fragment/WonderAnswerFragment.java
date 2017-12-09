package com.laoodao.smartagri.ui.qa.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.bean.Ask;
import com.laoodao.smartagri.bean.MyAnswer;
import com.laoodao.smartagri.bean.WonderAnswer;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.event.WonderEvent;
import com.laoodao.smartagri.ui.qa.adapter.MyAnswerAdapter;
import com.laoodao.smartagri.ui.qa.adapter.MyAskAdapter;
import com.laoodao.smartagri.ui.qa.adapter.WonderAnswerAdapter;
import com.laoodao.smartagri.ui.qa.contract.WonderAnswerContract;
import com.laoodao.smartagri.ui.qa.presenter.WonderAnswerPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WORK on 2017/4/24.
 */

public class WonderAnswerFragment extends BaseXRVFragment<WonderAnswerPresenter> implements WonderAnswerContract.WonderAnswerView {


    @Override
    public int getLayoutResId() {
        return R.layout.common_ft_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        initAdapter(WonderAnswerAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
        decoration.setFistMargin(UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    protected void lazyFetchData() {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userChanger(UserInfoChangedEvent event) {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wonderChange(WonderEvent event) {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser)
//        onRefresh();
//    }

    @Override
    public void showWonderAnswerList(List<WonderAnswer> wonderAnswerList, boolean isRefresh) {
        mAdapter.addAll(wonderAnswerList, isRefresh);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getWonderAnswerList(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getWonderAnswerList(page);
    }
}
