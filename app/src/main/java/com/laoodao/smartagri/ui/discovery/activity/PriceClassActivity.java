package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.PriceQuotationAdapter;
import com.laoodao.smartagri.ui.discovery.contract.PriceClassContract;
import com.laoodao.smartagri.ui.discovery.presenter.PriceClassPresenter;
import com.laoodao.smartagri.utils.UiUtils;

public class PriceClassActivity extends BaseXRVActivity<PriceClassPresenter> implements PriceClassContract.PriceClassView {


    private int areaId;//城市id
    private int cropId;//分类id
    private int timeId;//时间id
    private String pos = "";
    private SelectLocation currentLocation;
    private String cityId;

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
        return R.layout.activity_price_class;
    }

    public static void start(Context context, int id, String titile) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("titile", titile);
        UiUtils.startActivity(context, PriceClassActivity.class, bundle);
    }

    @Override
    protected void configViews() {
        timeId = 0;
        areaId = 0;
        cropId = getIntent().getIntExtra("id", 0);
        setTitle(getIntent().getStringExtra("titile"));
        currentLocation = Global.getInstance().getCurrentLocation();
        if (currentLocation != null) {
            pos = currentLocation.longitude + "," + currentLocation.latitude;
        }
        initAdapter(PriceQuotationAdapter.class);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestData(cropId, page, timeId, "", pos, areaId);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestData(cropId, page, timeId, "", pos, areaId);
    }
}