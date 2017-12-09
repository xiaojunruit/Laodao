package com.laoodao.smartagri.ui.user.activity;

import android.support.v4.content.ContextCompat;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.IntegralTask;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.IntegralTaskAdapter;
import com.laoodao.smartagri.ui.user.contract.IntegralTaskContract;
import com.laoodao.smartagri.ui.user.presenter.IntegralTaskPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 签到->赚更多
 */
public class IntegralTaskActivity extends BaseXRVActivity<IntegralTaskPresenter> implements IntegralTaskContract.IntegralTaskView {


    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_xrecyclerview;
    }

    @Override
    protected void configViews() {
        initAdapter(IntegralTaskAdapter.class);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.transparent), UiUtils.dip2px(10));
        decoration.setFistMargin(UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mPresenter.getTaskList();
    }


    @Override
    public void showTaskList(List<IntegralTask> taskList) {
        mAdapter.addAll(taskList, true);
    }
}