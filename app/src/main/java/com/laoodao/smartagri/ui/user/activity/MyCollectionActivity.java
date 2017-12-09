package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.qa.fragment.FollowContentFragment;
import com.laoodao.smartagri.ui.qa.fragment.MyQATabLayoutFragment;
import com.laoodao.smartagri.ui.qa.fragment.NewestQuestionFragment;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.ui.user.contract.MyCollectionContract;
import com.laoodao.smartagri.ui.user.fragment.AskCollectionFragment;
import com.laoodao.smartagri.ui.user.fragment.SupplyCollectionFragment;
import com.laoodao.smartagri.ui.user.presenter.MyCollectionPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;

public class MyCollectionActivity extends BaseActivity<MyCollectionPresenter> implements MyCollectionContract.MyCollectionView {


    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private TabsAdapter mTabsAdapter;
    private int mMemberId;
    private int isSelf;

    public static void start(Context context, int memberId, int isSelf) {
        Bundle bundle = new Bundle();
        bundle.putInt("memberId", memberId);
        bundle.putInt("isSelf", isSelf);
        UiUtils.startActivity(context, MyCollectionActivity.class, bundle);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void configViews() {
        isSelf = getIntent().getIntExtra("isSelf", 0);
        mMemberId = getIntent().getIntExtra("memberId", 0);
        setTitle(isSelf == 0 ? "他的收藏" : "我的收藏");
        String[] title = getResources().getStringArray(R.array.user_collection);
        Fragment fragment[] = {AskCollectionFragment.newInstance(mMemberId), SupplyCollectionFragment.newInstance(mMemberId, 2), SupplyCollectionFragment.newInstance(mMemberId, 1)};
        mTabsAdapter = new TabsAdapter(getSupportFragmentManager(), fragment);
        mViewpager.setAdapter(mTabsAdapter);
        mTabLayout.setViewPager(mViewpager, title);
    }


    @Override
    public void complete() {

    }
}