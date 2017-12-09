package com.laoodao.smartagri.ui.qa.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.MyAnswer;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.AnswerChangeEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.qa.adapter.MyAnswerAdapter;
import com.laoodao.smartagri.ui.qa.contract.MyAnswerContract;
import com.laoodao.smartagri.ui.qa.presenter.MyAnswerPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 * 我的问答
 */

public class MyAnswerFragment extends BaseXRVFragment<MyAnswerPresenter> implements MyAnswerContract.MyAnswerQAView {


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
        initAdapter(MyAnswerAdapter.class);
       //XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
      //  mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    protected void lazyFetchData() {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userChanger(UserInfoChangedEvent event) {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void answerChange(AnswerChangeEvent event) {
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Override
    public void myAnswerSuccess(List<MyAnswer> myAnswers, boolean isRefresh) {

        mAdapter.addAll(myAnswers, isRefresh);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getMyAnswerList(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getMyAnswerList(page);
    }


}
