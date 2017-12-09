package com.laoodao.smartagri.ui.farmland.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.AccountList;
import com.laoodao.smartagri.bean.LedgerStatistics;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.BillChangeEvent;
import com.laoodao.smartagri.event.FarmlandChangeEvent;
import com.laoodao.smartagri.event.RemoveBillChangeEvent;
import com.laoodao.smartagri.ui.farmland.activity.RecordActivity;
import com.laoodao.smartagri.ui.farmland.adapter.ManagementAdapter;
import com.laoodao.smartagri.ui.farmland.contract.FarmlandManagementContract;
import com.laoodao.smartagri.ui.farmland.presenter.FarmlandManagementPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/24.
 */

public class FarmlandManagementFragment extends BaseXRVFragment<FarmlandManagementPresenter> implements FarmlandManagementContract.FarmlandManagementView {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_farmland_management;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    class FarmlandHeader extends BaseHeaderView {
        @BindView(R.id.tv_income)
        TextView mTvIncome;
        @BindView(R.id.tv_expenditure)
        TextView mTvExpenditure;
        @BindView(R.id.tv_difference)
        TextView mTvDifference;
        @BindView(R.id.accounting)
        FrameLayout mAccounting;

        public FarmlandHeader(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_farmland_management;
        }

        @OnClick(R.id.accounting)
        public void onClick() {
            UiUtils.startActivity(RecordActivity.class);
        }

    }

    @Override
    public void configViews() {
        mHeader = new FarmlandHeader(getContext());
        initAdapter(ManagementAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(1));
        mRecyclerView.addItemDecoration(decoration);
        onRefresh();

    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void billChange(BillChangeEvent event) {
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removeBillChange(RemoveBillChangeEvent event) {
        mAdapter.remove(event.position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void farmlandChange(FarmlandChangeEvent event) {
        onRefresh();
    }

    @Override
    public void initStatistics(LedgerStatistics statistics) {
        FarmlandHeader header = ((FarmlandHeader) mHeader);
        header.mTvDifference.setText(statistics.profit);
        header.mTvExpenditure.setText(statistics.expend);
        header.mTvIncome.setText(statistics.income);
    }

    @Override
    public void initAccountList(Page<AccountList> result, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(result.data.items);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestAccountList(page);
        mPresenter.requestStatistics();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestAccountList(page);
    }
}
