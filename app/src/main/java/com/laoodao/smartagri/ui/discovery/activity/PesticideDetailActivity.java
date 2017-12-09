package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.PesticideDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.ConstituteAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.UsageAdapter;
import com.laoodao.smartagri.ui.discovery.contract.PesticideDetailContract;
import com.laoodao.smartagri.ui.discovery.presenter.PesticideDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;

public class PesticideDetailActivity extends BaseActivity<PesticideDetailPresenter> implements PesticideDetailContract.PesticideDetailView {


    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_register_name)
    TextView mTvRegisterName;
    @BindView(R.id.tv_total_content)
    TextView mTvTotalContent;
    @BindView(R.id.tv_product_form)
    TextView mTvProductForm;
    @BindView(R.id.tv_toxic)
    TextView mTvToxic;
    @BindView(R.id.tv_manufacturer)
    TextView mTvManufacturer;
    @BindView(R.id.tv_country)
    TextView mTvCountry;
    @BindView(R.id.tv_expiry_start)
    TextView mTvExpiryStart;
    @BindView(R.id.tv_expiry_end)
    TextView mTvExpiryEnd;
    @BindView(R.id.tv_dose_desc)
    TextView mTvDoseDesc;
    @BindView(R.id.tv_product_usage)
    TextView mTvProductUsage;
    @BindView(R.id.tv_attention)
    TextView mTvAttention;
    @BindView(R.id.tv_poisoning_aid)
    TextView mTvPoisoningAid;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.tv_category)
    TextView mTvCategory;
    @BindView(R.id.tv_product_name)
    TextView mTvProductName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_website)
    TextView mTvWebsite;
    @BindView(R.id.tv_zipcode)
    TextView mTvZipcode;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_fax)
    TextView mTvFax;


    public ConstituteAdapter constituteAdapter;
    public UsageAdapter usageAdapter;
    @BindView(R.id.constitute_recyclerView)
    RecyclerView mConstituteRecyclerView;
    @BindView(R.id.usage_recyclerView)
    RecyclerView mUsageRecyclerView;


    private int mId;
    private PesticideDetail mDetail;

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
        UiUtils.startActivity(context, PesticideDetailActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pesticide_detail;
    }

    @Override
    protected void configViews() {
        mId = getIntent().getIntExtra("id", 0);
        constituteAdapter = new ConstituteAdapter(this);
        mConstituteRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        usageAdapter = new UsageAdapter(this);
        mUsageRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        onRefresh();
        mConstituteRecyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0));
        mUsageRecyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0));
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

    private void onRefresh() {
        mPresenter.requestDetail(mId);
    }

    @Override
    public void complete() {

    }

    @Override
    public void showDetail(PesticideDetail detail) {
        mDetail = detail;
        mTvNumber.setText(detail.number);
        mTvRegisterName.setText(detail.registerName);
        mTvTotalContent.setText(detail.totalContent);
        mTvProductForm.setText(detail.productForm);
        mTvToxic.setText(detail.toxic);
        mTvManufacturer.setText(detail.manufacturer);
        mTvCountry.setText(detail.country);
        mTvExpiryStart.setText(detail.expiryStart);
        mTvExpiryEnd.setText(detail.expiryEnd);
        mTvDoseDesc.setText(detail.doseDesc);
        mTvProductUsage.setText(detail.productUsage);
        mTvAttention.setText(detail.attention);
        mTvPoisoningAid.setText(detail.poisoningAid);
        mTvCategory.setText(detail.category);
        mTvProductName.setText(detail.productName);
        mTvAddress.setText(detail.address);
        mTvWebsite.setText(detail.website);
        mTvZipcode.setText(detail.zipcode);
        mTvPhone.setText(detail.phone);
        mTvFax.setText(detail.fax);
        constituteAdapter.addAll(detail.activePrinciple);
        mConstituteRecyclerView.setAdapter(constituteAdapter);
        usageAdapter.addAll(detail.dose);
        mUsageRecyclerView.setAdapter(usageAdapter);

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


}