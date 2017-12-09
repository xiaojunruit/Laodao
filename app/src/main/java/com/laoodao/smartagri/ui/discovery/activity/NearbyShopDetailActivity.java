package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejz.multistateview.MultiStateView;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.NearbyShopDetail;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.NearbyShopDetailContract;
import com.laoodao.smartagri.ui.discovery.presenter.NearbyShopDetailPresenter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/3.
 */

public class NearbyShopDetailActivity extends BaseActivity<NearbyShopDetailPresenter> implements NearbyShopDetailContract.NearbyShopDetailView {
    @BindView(R.id.iv_img)
    RoundedImageView mIvImg;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_distance)
    TextView mTvDistance;
    @BindView(R.id.tv_address_detail)
    TextView mTvAddressDetail;
    @BindView(R.id.tv_contact)
    TextView mTvContact;
    @BindView(R.id.tv_management)
    TextView mTvManagement;
    @BindView(R.id.rtv_follow)
    RoundTextView mRtvFollow;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private String num;
    private String id;
    private NearbyShopDetail mDetail;
    private String mStroeId;


    public static void start(Context context, String id, String stroeId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("stroeId", stroeId);
        UiUtils.startActivity(context, NearbyShopDetailActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby_shop_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        id = getIntent().getStringExtra("id");
        mStroeId = getIntent().getStringExtra("stroeId");
        SelectLocation location = Global.getInstance().getCurrentLocation();
        mPresenter.requestNearbyShopDetail(id, location.latitude, location.longitude);
//        mPresenter.requestNearbyShopDetail(id, String.valueOf();
//        BDLocation bdLocation = Global.getInstance().getBdLocation();
//        if (bdLocation == null) {
//            return;
//        }
//        mPresenter.requestNearbyShopDetail(id, String.valueOf(bdLocation.getLatitude()), String.valueOf(bdLocation.getLongitude()));
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mPresenter.requestNearbyShopDetail(id, location.latitude, location.longitude);
            });
        }
    }

    private int mWonder;

    @Override
    public void nearbyShopDetailSuccess(NearbyShopDetail detial) {
        if (!TextUtils.isEmpty(detial.logo)) {
            Glide.with(this).load(detial.logo).into(mIvImg);
        }
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_shop_authentication);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvTitle.setCompoundDrawables(null, null, detial.certification == 1 ? drawable : null, null);
        mTvTitle.setText(detial.storeName);
        mTvAddress.setText(detial.areaInfo);
        mTvDistance.setText(detial.distanceStr);
        mTvContact.setText(UiUtils.getString(R.string.contact_information, detial.storePhone));
        mTvAddressDetail.setText(UiUtils.getString(R.string.address, detial.storeAddr));
        mTvManagement.setText(TextUtils.isEmpty(detial.products) ? UiUtils.getString(R.string.management_goods, "暂无信息") : UiUtils.getString(R.string.management_goods, detial.products));
        num = detial.storePhone;
        mDetail = detial;
        setFollowStyle(detial.isWonder);
        mWonder = detial.isWonder;
    }

    private void setFollowStyle(int isWonder) {
        if (isWonder == 0) {
            Drawable followDrawable = ContextCompat.getDrawable(this, R.mipmap.ic_add);
            followDrawable.setBounds(0, 0, UiUtils.dip2px(10), UiUtils.dip2px(10));
            mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            mRtvFollow.setCompoundDrawables(followDrawable, null, null, null);
            mRtvFollow.setText("关注");
        } else {
            mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.common_h5));
            mRtvFollow.setCompoundDrawables(null, null, null, null);
            mRtvFollow.setText("已关注");
        }
    }

    @Override
    public void successFollow() {
        if (mWonder == 1) {
            mWonder = 0;
            setFollowStyle(mWonder);
        } else {
            mWonder = 1;
            setFollowStyle(mWonder);
        }
    }

    @OnClick({R.id.tv_contact, R.id.tv_address_detail, R.id.rtv_follow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address_detail:
                NearbyShopMapActivity.start(this, mDetail);
                break;
            case R.id.tv_contact:
                if (TextUtils.isEmpty(num)) {
                    return;
                }
                DeviceUtils.openDial(getApplicationContext(), num);
                break;
            case R.id.rtv_follow:
                mPresenter.requestFollow(mStroeId);
                break;
        }

    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
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
