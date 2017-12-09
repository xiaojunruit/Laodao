package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.DiscoveryMenuAdapter;
import com.laoodao.smartagri.ui.discovery.contract.MenuMoreContract;
import com.laoodao.smartagri.ui.discovery.presenter.MenuMorePresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.SpaceDecoration;

import java.util.List;

public class MenuMoreActivity extends BaseXRVActivity<MenuMorePresenter> implements MenuMoreContract.MenuMoreView {


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
        return R.layout.discover_menu_more;
    }

    @Override
    protected void configViews() {
        initAdapter(DiscoveryMenuAdapter.class);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        mRecyclerView.setLoadingMoreEnabled(false);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.allMenuRequest(1);
    }

    @Override
    public void allMenu(List<Discovercat> data) {
        mAdapter.addAll(data, true);
    }
}