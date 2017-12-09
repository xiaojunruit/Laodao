package com.laoodao.smartagri.ui.qa.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.annotations.SerializedName;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.qa.contract.MyQATabLayoutContract;
import com.laoodao.smartagri.ui.qa.presenter.MyQATabLayoutPresenter;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyQATabLayoutFragment extends LazyFragment<MyQATabLayoutPresenter> implements MyQATabLayoutContract.MyQAView {
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tv_login)
    RoundTextView mTvLogin;
    @BindView(R.id.no_login)
    LinearLayout mNoLogin;
    private TabsAdapter mTabAdapter;

    @Override
    public void complete() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_qa_tablayout;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginChange(UserInfoChangedEvent event) {
        mNoLogin.setVisibility(!Global.getInstance().isLoggedIn() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @OnClick(R.id.tv_login)
    public void onClick() {
        if (!Global.getInstance().isLoggedIn()) {
            UiUtils.startActivity(LoginActivity.class);
            return;
        }
    }

    @Override
    protected void lazyFetchData() {
        String[] titles = getResources().getStringArray(R.array.qa_tablayout);
        BaseFragment[] fragments = {new MyAskFragment(), new MyAnswerFragment(), new WonderAnswerFragment(), new MyWonderUsersFragment()};
        mTabAdapter = new TabsAdapter(getChildFragmentManager(), fragments);
        mViewpager.setAdapter(mTabAdapter);
        mTabLayout.setViewPager(mViewpager, titles);
        mTabLayout.setCurrentTab(0);
        mNoLogin.setVisibility(!Global.getInstance().isLoggedIn() ? View.VISIBLE : View.GONE);
    }
}
