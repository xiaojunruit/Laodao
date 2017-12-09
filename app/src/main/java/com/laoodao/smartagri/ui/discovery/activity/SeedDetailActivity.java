package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.SeedDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.SeedDetailContract;
import com.laoodao.smartagri.ui.discovery.presenter.SeedDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

public class SeedDetailActivity extends BaseActivity<SeedDetailPresenter> implements SeedDetailContract.SeedDetailView {


    @BindView(R.id.tv_category)
    TextView mTvCategory;
    @BindView(R.id.tv_variety)
    TextView mTvVariety;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.tv_organization)
    TextView mTvOrganization;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    @BindView(R.id.tv_transgenosis)
    TextView mTvTransgenosis;
    @BindView(R.id.tv_source)
    TextView mTvSource;
    @BindView(R.id.tv_feature)
    TextView mTvFeature;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.tv_yield)
    TextView mTvYield;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    public int mId;
    public SeedDetail mDetail;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, SeedDetailActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seed_detail;
    }

    @Override
    protected void configViews() {
        mId = getIntent().getIntExtra("id", 0);
        onRefresh();
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });
            mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });
        }

    }

    public void onRefresh() {
        mPresenter.requestDetail(mId);
    }

    @Override
    public void complete() {

    }


    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (mDetail == null) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

    }

    @Override
    public void showDetail(SeedDetail detail) {
        mDetail = detail;
        mTvCategory.setText(detail.category);
        mTvVariety.setText(detail.variety);
        mTvNumber.setText(detail.number);
        mTvYear.setText(detail.year);
        mTvOrganization.setText(detail.organization);
        mTvCompany.setText(detail.company);
        mTvTransgenosis.setText(detail.transgenosis);
        mTvSource.setText(detail.source);
        mTvSource.setVisibility(TextUtils.isEmpty(detail.source) ? View.GONE : View.VISIBLE);
        mTvFeature.setText(detail.feature);
        mTvFeature.setVisibility(TextUtils.isEmpty(detail.feature) ? View.GONE : View.VISIBLE);
        mTvArea.setText(detail.area);
        mTvArea.setVisibility(TextUtils.isEmpty(detail.area) ? View.GONE : View.VISIBLE);
        mTvYield.setText(detail.yield);
        mTvYield.setVisibility(TextUtils.isEmpty(detail.yield) ? View.GONE : View.VISIBLE);
    }
}