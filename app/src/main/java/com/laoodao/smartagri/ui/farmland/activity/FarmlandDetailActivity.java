package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseRVActivity;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.FarmlandDetail;
import com.laoodao.smartagri.bean.FarmlandDetailInfo;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.FarmlandChangeEvent;
import com.laoodao.smartagri.ui.farmland.adapter.FarmlandDetailAdapter;
import com.laoodao.smartagri.ui.farmland.contract.FarmlandDetailContract;
import com.laoodao.smartagri.ui.farmland.presenter.FarmlandDetailPresenter;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/24.
 * 农田详情
 */

public class FarmlandDetailActivity extends BaseXRVActivity<FarmlandDetailPresenter> implements FarmlandDetailContract.FarmlandDetailView {
    @BindView(R.id.tv_crop)
    TextView mTvCrop;
    @BindView(R.id.tv_acreage)
    TextView mTvAcreage;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_edit_farmland)
    TextView mTvEditFarmland;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    private int id;
    private FarmlandDetailInfo detailInfo;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, FarmlandDetailActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_farmland_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    class FarmlandDetailHeader extends BaseHeaderView {
        public Context mContext;
        @BindView(R.id.btn_add_operation)
        LinearLayout mBtnAddOperation;
        @BindView(R.id.view_left)
        View mViewLeft;

        public FarmlandDetailHeader(Context context) {
            super(context);
            mContext = context;
        }

        @OnClick(R.id.btn_add_operation)
        public void onClick() {
            AddOperationActivity.start(mContext, 0, id);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_farmland_detail;
        }
    }

    @Override
    protected void configViews() {
        mHeader = new FarmlandDetailHeader(this);


        id = getIntent().getIntExtra("id", 0);
        initAdapter(FarmlandDetailAdapter.class);
        mAdapter.setOnItemClickListener(position -> {
            List<FarmlandDetail> list = mAdapter.getAllData();
            FarmlandDetail farmlandDetail = list.get(position);
            AddOperationActivity.start(this, farmlandDetail.id, id);
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestFarmlandDetail(id);
        mPresenter.requestFarmlandDetailList(page, id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void farmlandChange(FarmlandChangeEvent event) {
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestFarmlandDetailList(page, id);
    }

    @Override
    public void requestFarmlandDetailListSuccess(List<FarmlandDetail> farmlandDetails, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(farmlandDetails);
        FarmlandDetailHeader header = ((FarmlandDetailHeader) mHeader);
        header.mViewLeft.setVisibility(farmlandDetails.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void requestFarmlandDetailSuccess(FarmlandDetailInfo info) {
        this.detailInfo = info;
        mTvCrop.setText(info.cropsName);
        mTvAcreage.setText(UiUtils.getString(R.string.mu, info.acreage));
        mTvAddress.setText(UiUtils.getString(R.string.position, info.fulladdress));
        mTvMoney.setText(UiUtils.getString(R.string.consumption, info.countMoney));
    }

    @OnClick({R.id.tv_edit_farmland, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit_farmland:
                AddFarmlandActivity.start(this, detailInfo);
                break;
        }

    }

}
