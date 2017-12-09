package com.laoodao.smartagri.ui.discovery.fragment;

import android.os.Bundle;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.SevenChartContract;
import com.laoodao.smartagri.ui.discovery.presenter.SevenChartPresenter;
import com.laoodao.smartagri.view.StatisticalChartView;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/18.
 */

public class SevenChartFragment extends BaseFragment<SevenChartPresenter> implements SevenChartContract.SevenChartView {
    @BindView(R.id.chartView)
    StatisticalChartView mChartView;

    @Override
    public void complete() {

    }

    public static SevenChartFragment newInstance(float[] price, String[] data, float[] pricesToday, String titile) {
        Bundle args = new Bundle();
        SevenChartFragment fragment = new SevenChartFragment();
        args.putFloatArray("price", price);
        args.putStringArray("data", data);
        args.putFloatArray("pricesToday", pricesToday);
        args.putString("titile", titile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_seven_chart;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    public void configViews() {
        mChartView.setData(getArguments().getFloatArray("price"), getArguments().getStringArray("data"), getArguments().getFloatArray("pricesToday"), getArguments().getString("titile"));
    }

}