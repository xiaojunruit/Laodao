package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Fertilizer;
import com.laoodao.smartagri.bean.Seed;
import com.laoodao.smartagri.bean.TruthQuery;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.QueryFertilizerAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.QuerySeedAdapter;
import com.laoodao.smartagri.ui.discovery.contract.QueryFertilizerContract;
import com.laoodao.smartagri.ui.discovery.presenter.QueryFertilizerPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class QueryFertilizerActivity extends BaseXRVActivity<QueryFertilizerPresenter> implements QueryFertilizerContract.QueryResultView {
    private String mNumber = "";
    private String mCommonName = "";
    private String mCompany = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_query_result;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, String number, String commonName, String company) {
        Bundle bundle = new Bundle();
        bundle.putString("number", number);
        bundle.putString("commonName", commonName);
        bundle.putString("company", company);
        UiUtils.startActivity(context, QueryFertilizerActivity.class, bundle);
    }

    @Override
    protected void configViews() {
        mNumber = getIntent().getStringExtra("number");
        mCommonName = getIntent().getStringExtra("commonName");
        mCompany = getIntent().getStringExtra("company");
        initAdapter(QueryFertilizerAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getApplicationContext(), R.color.common_divider_narrow), UiUtils.dip2px(1), 45, 0);
        mRecyclerView.addItemDecoration(decoration);
        onRefresh();
    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestFertilizer(page, mNumber, mCommonName, mCompany);

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestFertilizer(page, mNumber, mCommonName, mCompany);
    }


    @Override
    public void queryFertilizer(List<TruthQuery> data, boolean isRefresh) {
        if (isRefresh) mAdapter.clear();
        mAdapter.addAll(data);
    }
}
