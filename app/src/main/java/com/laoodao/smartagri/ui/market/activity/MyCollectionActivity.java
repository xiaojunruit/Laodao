package com.laoodao.smartagri.ui.market.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.ui.market.contact.MyCollectionContact;
import com.laoodao.smartagri.ui.market.fragment.CollectionBuyFragment;
import com.laoodao.smartagri.ui.market.fragment.CollectionSalesFragment;
import com.laoodao.smartagri.ui.market.presenter.MyCollectionPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/18.
 * 我的发布
 */

public class MyCollectionActivity extends BaseActivity<MyCollectionPresenter> implements MyCollectionContact.MyCollectionView {


    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_release;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        initTabLayout();
    }

    private void initTabLayout() {
        String[] tabTitle = getResources().getStringArray(R.array.collection_tab);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CollectionBuyFragment());
        fragments.add(new CollectionSalesFragment());
        mTabLayout.setViewPager(mViewpager, tabTitle, this, fragments);
    }

    @OnClick(R.id.tabLayout)
    public void onClick() {
    }
}
