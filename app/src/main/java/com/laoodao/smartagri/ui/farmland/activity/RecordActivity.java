package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.farmland.contract.RecordContract;
import com.laoodao.smartagri.ui.farmland.fragment.IncomeRecordFragment;
import com.laoodao.smartagri.ui.farmland.presenter.RecordPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/23.
 * 收入支出账单
 */

public class RecordActivity extends BaseActivity<RecordPresenter> implements RecordContract.FarmIncomeView {
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String tabtitle[];
    @BindView(R.id.tabLayout)
    SegmentTabLayout mTabLayout;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        initTabLayout();
    }

    public void initTabLayout() {
        tabtitle = getResources().getStringArray(R.array.MyFarmland_tab);
        mFragments.add(IncomeRecordFragment.newInstance(Constant.EXPENDITURE));
        mFragments.add(IncomeRecordFragment.newInstance(Constant.INCOME));
        mViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setTabData(tabtitle);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.img_back, R.id.tv_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_complete:
                ((IncomeRecordFragment) mFragments.get(mTabLayout.getCurrentTab())).submit();
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragments.get(mTabLayout.getCurrentTab()).onActivityResult(requestCode & 0xffff, resultCode, data);
    }
}
