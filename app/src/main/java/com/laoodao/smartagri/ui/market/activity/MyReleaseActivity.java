package com.laoodao.smartagri.ui.market.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.event.ShareIdEvent;
import com.laoodao.smartagri.ui.market.contact.MyReleaseContact;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.market.fragment.ReleaseBuyFragment;
import com.laoodao.smartagri.ui.market.fragment.ReleaseSupplyFragment;
import com.laoodao.smartagri.ui.market.presenter.MyReleasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/18.
 * 我的发布
 */

public class MyReleaseActivity extends BaseActivity<MyReleasePresenter> implements MyReleaseContact.MyReleaseView {

    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private ShareDialog shareDialog;
    private boolean layoutVisible;
    private int supplyId;

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
        String[] tabTitle = getResources().getStringArray(R.array.release_tab);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ReleaseBuyFragment());
        fragments.add(new ReleaseSupplyFragment());
        mTabLayout.setViewPager(mViewPager, tabTitle, this, fragments);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(shareDialog!=null) { //判断为空
            shareDialog.onActivityResult(requestCode, resultCode, data);
        }


    }


    @Subscribe
    public void shareQuestion(ShareIdEvent event) {
        supplyId = event.id;
        if (shareDialog == null) {
            shareDialog = event.shareDialog;
        }
    }


    @Subscribe
    public void shareSuccessEvent(ShareEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (layoutVisible) {
                    mPresenter.share("supply", supplyId);
                }
            }
        }, 500);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        layoutVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutVisible = true;

    }
}
