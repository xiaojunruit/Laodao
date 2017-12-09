package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Enterprise.Enterprise;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.EnterpriseRefreshCode;
import com.laoodao.smartagri.ui.discovery.contract.CottonQueryResultContract;
import com.laoodao.smartagri.ui.discovery.fragment.NormalEnterpriseFragment;
import com.laoodao.smartagri.ui.discovery.presenter.CottonQueryResultPresenter;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class CottonQueryResultActivity extends BaseActivity<CottonQueryResultPresenter> implements CottonQueryResultContract.QueryResultView {


    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private TabsAdapter mTabsAdapter;
    private Enterprise enterprise;

    public static void start(Context context, Enterprise enterprise) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("enterprise", enterprise);
        UiUtils.startActivity(context, CottonQueryResultActivity.class, bundle);
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
    protected int getLayoutId() {
        return R.layout.activity_cotton_query_result;
    }

    @Override
    protected void configViews() {
        enterprise = (Enterprise) getIntent().getSerializableExtra("enterprise");
        initTab();
    }


    @Override
    public void complete() {

    }

    private void initTab() {
        String[] title = getResources().getStringArray(R.array.cotton_query_result);
        Fragment[] fragments = {NormalEnterpriseFragment.newInstance(1, enterprise), NormalEnterpriseFragment.newInstance(2, enterprise)};
        mTabsAdapter = new TabsAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mTabsAdapter);
        mTabLayout.setViewPager(mViewpager, title);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new EnterpriseRefreshCode());
        super.onDestroy();
    }


}