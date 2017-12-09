package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.IntegralDetailAdapter;
import com.laoodao.smartagri.ui.user.contract.IntegralDetailContract;
import com.laoodao.smartagri.ui.user.presenter.IntegralDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class IntegralDetailActivity extends BaseXRVActivity<IntegralDetailPresenter> implements IntegralDetailContract.IntegralDetailView {


    public static void start(Context context, String integral) {
        Bundle bundle = new Bundle();
        bundle.putString("integral", integral);
        UiUtils.startActivity(context, IntegralDetailActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initData(Page<IntegralDetail> data, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(data);
    }

    class IntegralDetailHeader extends BaseHeaderView {

        @BindView(R.id.tv_total_money)
        TextView mTvTotalMoney;

        public IntegralDetailHeader(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_integral;
        }
    }

    @Override
    protected void configViews() {
        mHeader = new IntegralDetailHeader(this);
        Bundle bundle = this.getIntent().getExtras();
        initAdapter(IntegralDetailAdapter.class);
        IntegralDetailHeader header = ((IntegralDetailHeader) mHeader);
        header.mTvTotalMoney.setText(bundle.getString("integral"));
        onRefresh();

    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestData(page);

    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestData(page);

    }

}
