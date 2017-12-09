package com.laoodao.smartagri.ui.discovery.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.CottonQueryContract;
import com.laoodao.smartagri.ui.discovery.fragment.EnterpriseInformationFragment;
import com.laoodao.smartagri.ui.discovery.fragment.ReportInformationFragment;
import com.laoodao.smartagri.ui.discovery.presenter.CottonQueryPresenter;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;

import butterknife.BindView;

public class CottonQueryActivity extends BaseActivity<CottonQueryPresenter> implements CottonQueryContract.CottonQueryView {


    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    private TabsAdapter mTabsAdapter;

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
        return R.layout.activity_cotton_query;
    }

    @Override
    protected void configViews() {
        initTab();
    }


    @Override
    public void complete() {

    }

    public void initTab() {
        String[] tabTitle = getResources().getStringArray(R.array.cotton_query);
        Fragment[] fragments = {new ReportInformationFragment(), new EnterpriseInformationFragment()};
        mTabsAdapter = new TabsAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mTabsAdapter);
        mTabLayout.setViewPager(mViewpager, tabTitle);
    }


}