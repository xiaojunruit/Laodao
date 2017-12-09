package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Pesticide;
import com.laoodao.smartagri.bean.TruthQuery;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.PesticideAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.QueryFertilizerAdapter;
import com.laoodao.smartagri.ui.discovery.contract.PesticideContract;
import com.laoodao.smartagri.ui.discovery.presenter.PesticidePresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

public class PesticideActivity extends BaseXRVActivity<PesticidePresenter> implements PesticideContract.PesticideView {

    private String mNumber;
    private String mActivePrinciple;
    private String mDose;
    private String mManufacturer;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public static void start(Context context, String number, String activePrinciple, String dose, String manufacturer) {
        Bundle bundle = new Bundle();
        bundle.putString("number", number);
        bundle.putString("activePrinciple", activePrinciple);
        bundle.putString("dose", dose);
        bundle.putString("manufacturer", manufacturer);
        UiUtils.startActivity(context, PesticideActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_query_result;
    }

    @Override
    protected void configViews() {
        mNumber = getIntent().getStringExtra("number");
        mActivePrinciple = getIntent().getStringExtra("activePrinciple");
        mDose = getIntent().getStringExtra("dose");
        mManufacturer = getIntent().getStringExtra("manufacturer");
        initAdapter(PesticideAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getApplicationContext(), R.color.common_divider_narrow), UiUtils.dip2px(1), 45, 0);
        mRecyclerView.addItemDecoration(decoration);
        onRefresh();
    }

    @Override
    public void queryPesticide(List<TruthQuery> data, boolean isRefresh) {
        if (isRefresh) mAdapter.clear();
        mAdapter.addAll(data);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestPesticide(page, mNumber, mActivePrinciple, mDose, mManufacturer);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestPesticide(page, mNumber, mActivePrinciple, mDose, mManufacturer);
    }
}