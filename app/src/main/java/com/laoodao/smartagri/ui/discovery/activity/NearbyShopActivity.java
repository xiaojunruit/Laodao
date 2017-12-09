package com.laoodao.smartagri.ui.discovery.activity;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ejz.xrecyclerview.XRecyclerView;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.discovery.adapter.NearbyShopAdapter;
import com.laoodao.smartagri.ui.discovery.contract.NearbyShopContract;
import com.laoodao.smartagri.ui.discovery.presenter.NearbyShopPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/5/3.
 */

public class NearbyShopActivity extends BaseXRVActivity<NearbyShopPresenter> implements NearbyShopContract.NearbyShopView {

    private String mLatitude;
    private String mLongitude;
    @BindView(R.id.no_location)
    LinearLayout mNoLocation;
    @BindView(R.id.tv_follow)
    RoundTextView mTvFollow;
    @BindView(R.id.tv_error_mark)
    TextView mTvErrorMark;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby_shop;
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
        initAdapter(NearbyShopAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.transparent), 1);
        mRecyclerView.addItemDecoration(decoration);
        //已经手动选择过定位
        SelectLocation selectLocation = Global.getInstance().getCurrentLocation();
        if (selectLocation != null) {
            mLatitude = selectLocation.latitude;
            mLongitude = selectLocation.longitude;
            onRefresh();
        } else {
            requestLocation();
        }


    }

    @OnClick(R.id.tv_follow)
    public void onClick() {
        if (!PermissionUtil.judgeLocation(new RxPermissions(this))) {
            PermissionUtil.startActionAetailsSettings(this);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        RxLocation.get().locate(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation bdLocation) {
                        Global.getInstance().setCurrentLocation(bdLocation);
                        onRefresh();
                        mNoLocation.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                        mNoLocation.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionUtil.judgeLocation(new RxPermissions(this))) {
            mTvErrorMark.setText(Html.fromHtml("定位服务暂未开启<br/>\n" +
                    "请在系统设置中开启定位服务"));
            mTvFollow.setText("去设置");
            mNoLocation.setVisibility(View.VISIBLE);
        } else if (Global.getInstance().getCurrentLocation() == null) {
            mTvErrorMark.setText("定位失败");
            mTvFollow.setText("重试");
            mNoLocation.setVisibility(View.VISIBLE);
        } else if (mNoLocation.getVisibility() == View.VISIBLE) {
            mNoLocation.setVisibility(View.GONE);
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (!PermissionUtil.judgeLocation(new RxPermissions(this))) {
            complete();
            return;
        }
        mNoLocation.setVisibility(View.GONE);
        mPresenter.requestNearbyShop(page, mLatitude, mLongitude);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (!PermissionUtil.judgeLocation(new RxPermissions(this))) {
            return;
        }
        mPresenter.requestNearbyShop(page, mLatitude, mLongitude);
    }

//    @Override
//    public void nearbyShopSuccess(List<NearbyShop> nearbyShops, boolean isRefresh) {
//        if (isRefresh) {
//            mAdapter.clear();
//        }
//        mAdapter.addAll(nearbyShops);
//    }
}
