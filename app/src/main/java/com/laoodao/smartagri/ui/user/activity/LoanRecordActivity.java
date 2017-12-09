package com.laoodao.smartagri.ui.user.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.ui.user.contract.LoanRecordContract;
import com.laoodao.smartagri.ui.user.fragment.LoanRecordListFragment;
import com.laoodao.smartagri.ui.user.fragment.TradeRecordFragment;
import com.laoodao.smartagri.ui.user.presenter.LoanRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Long-PC on 2017/4/14.
 * 贷款记录Activity
 */

public class LoanRecordActivity extends BaseActivity<LoanRecordPresenter> implements LoanRecordContract.LoanRecordView {
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private TabsAdapter mTabAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loan_record;
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

    private void initTabLayout() {
        String[] tabTitle = getResources().getStringArray(R.array.loan_record_array);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoanRecordListFragment());
        fragments.add(new TradeRecordFragment());
        mTabLayout.setViewPager(mViewpager, tabTitle, this, fragments);
    }

    @Override
    public void complete() {

    }
}
