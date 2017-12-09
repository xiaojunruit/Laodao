package com.laoodao.smartagri.ui.user.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.ui.user.contract.MyOrderContract;
import com.laoodao.smartagri.ui.user.presenter.MyOrderPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/28.
 */

public class MyOrderFragment extends BaseFragment<MyOrderPresenter> implements MyOrderContract.MyOrderView {


    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private TabsAdapter mTabAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        String[] titles = getResources().getStringArray(R.array.my_order_array);
        Fragment[] fragments = {MyOrderListFragment.newInstance(Constant.ALL), MyOrderListFragment.newInstance(Constant.ARREARS), MyOrderListFragment.newInstance(Constant.REPAYMENT), MyOrderListFragment.newInstance(Constant.ALREADY_COMPLETED)};
        mTabAdapter = new TabsAdapter(getChildFragmentManager(), fragments);
        mViewpager.setAdapter(mTabAdapter);
        mTabLayout.setViewPager(mViewpager, titles);
    }


    @Override
    public void complete() {

    }
}
