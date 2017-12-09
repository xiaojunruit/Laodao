package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.MicrobialFertilizerDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.MicrobialFertilizerDetailContract;
import com.laoodao.smartagri.ui.discovery.presenter.MicrobialFertilizerDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

public class MicrobialFertilizerDetailActivity extends BaseActivity<MicrobialFertilizerDetailPresenter> implements MicrobialFertilizerDetailContract.MicrobialFertilizerDetailView {


    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    @BindView(R.id.tv_common_name)
    TextView mTvCommonName;
    @BindView(R.id.tv_product_name)
    TextView mTvProductName;
    @BindView(R.id.tv_product_form)
    TextView mTvProductForm;
    @BindView(R.id.tv_qualification)
    TextView mTvQualification;
    @BindView(R.id.tv_crop)
    TextView mTvCrop;
    @BindView(R.id.tv_expiry_date)
    TextView mTvExpiryDate;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private int id = 0;
    private MicrobialFertilizerDetail mDetail;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_microbial_fertilizer_detail;
    }

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, MicrobialFertilizerDetailActivity.class, bundle);
    }


    @Override
    protected void configViews() {
        id = getIntent().getIntExtra("id", 0);
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
        mPresenter.requestDetail(id);
    }

    @Override
    public void getMicrobialFertilizerDetail(MicrobialFertilizerDetail detail) {
        mDetail = detail;
        mTvNumber.setText(detail.number.replace("\n", ""));
        mTvCompany.setText(detail.company);
        mTvCommonName.setText(detail.commonName);
        mTvProductName.setText(detail.productName);
        mTvProductForm.setText(detail.productForm);
        mTvQualification.setText(detail.qualification);
        mTvCrop.setText(detail.crop);
        mTvExpiryDate.setText(detail.expiryDate);

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
    public void complete() {

    }
}