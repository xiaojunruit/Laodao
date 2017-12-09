package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.ui.market.activity.MarketSearchActivity;
import com.laoodao.smartagri.ui.market.contact.MarketContact;
import com.laoodao.smartagri.ui.market.dialog.ReleaseSupplyDemandDialog;
import com.laoodao.smartagri.ui.market.dialog.ReleaseSupplyDialog;
import com.laoodao.smartagri.ui.market.presenter.MarketPresenter;
import com.laoodao.smartagri.ui.user.activity.SettingActivity;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/13.
 * 供求
 */

public class MarketFragment extends LazyFragment<MarketPresenter> implements MarketContact.MarketView {
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private TabsAdapter mTabAdapter;

    //    private ReleaseSupplyDemandDialog mReleaseSupplyDemandDialog;
    private ReleaseSupplyDialog mReleaseSupplyDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
    }

    private void initTabLayout() {
        String[] tabTitle = getResources().getStringArray(R.array.market_tab);
        Fragment[] fragments = {new BuyFragment(), new SalesFragment(), new MySupplyDemandFragment()};


        mTabAdapter = new TabsAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setViewPager(mViewPager, tabTitle);
    }

    @OnClick({R.id.iv_search, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                UiUtils.startActivity(mActivity, MarketSearchActivity.class);
                break;
            case R.id.tv_add:
//                if (mReleaseSupplyDemandDialog == null) {
//                    mReleaseSupplyDemandDialog = new ReleaseSupplyDemandDialog(mActivity);
//                }
//                mReleaseSupplyDemandDialog.show();
                if (mReleaseSupplyDialog == null) {
                    mReleaseSupplyDialog = new ReleaseSupplyDialog(mActivity);
                }
                mReleaseSupplyDialog.show();
                break;
        }
    }

    @Override
    public void complete() {

    }

    @Override
    protected void lazyFetchData() {
        initTabLayout();
    }
}
