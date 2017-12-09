package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.MapReportingErrorsContract;
import com.laoodao.smartagri.ui.discovery.presenter.MapReportingErrorsPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/8/9.
 */

public class MapReportingErrorsActivity extends BaseActivity<MapReportingErrorsPresenter> implements MapReportingErrorsContract.MapReportingErrorsView {


    @BindView(R.id.ll_address_error)
    LinearLayout mLlAddressError;
    @BindView(R.id.ll_error)
    LinearLayout mLlError;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, MapReportingErrorsActivity.class, bundle);
    }

    @Override
    public void complete() {
        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_reporting_errors;
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
    }

    @OnClick({R.id.ll_address_error, R.id.ll_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address_error:
                MapFeedbackActivity.start(this, 5, id);
                break;
            case R.id.ll_error:
                MapFeedbackActivity.start(this, 6, id);
                break;
        }
    }
}
