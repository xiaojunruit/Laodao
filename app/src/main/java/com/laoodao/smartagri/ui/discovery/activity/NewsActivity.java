package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.ejz.multistateview.MultiStateView;
import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.NewsContract;
import com.laoodao.smartagri.ui.discovery.fragment.NewsListFragment;
import com.laoodao.smartagri.ui.discovery.presenter.NewsPresenter;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import com.laoodao.smartagri.ui.discovery.fragment.NewsListFragment;

public class NewsActivity extends BaseActivity<NewsPresenter> implements NewsContract.NewsView, ListBaseView {


    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private String[] titles;
    private BaseFragment[] fragments;
    private static final int NEWS_CODE = 1;
    private TabsAdapter mTabAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void configViews() {
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mPresenter.requestNewMenu();
            });
            mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mPresenter.requestNewMenu();
            });
        }
        mPresenter.requestNewMenu();
    }


    @Override
    public void complete() {

    }

    @OnClick(R.id.iv_add)
    public void onClick() {
        startActivityForResult(new Intent(NewsActivity.this, SelectNewsMenuActivity.class), NEWS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEWS_CODE) {
            if (resultCode == RESULT_OK) {
                mPresenter.requestNewMenu();
            }
        }
    }

    @Override
    public void newMenuSuccess(List<MechanicalType> mechanicalTypeList) {
        titles = null;
        fragments = null;
        fragments = new BaseFragment[mechanicalTypeList.size()];
        titles = new String[mechanicalTypeList.size()];
        for (int i = 0; i < mechanicalTypeList.size(); i++) {
            titles[i] = mechanicalTypeList.get(i).name;
            fragments[i] = NewsListFragment.newInstance(mechanicalTypeList.get(i).id);
        }
        mTabAdapter = new TabsAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setViewPager(mViewPager, titles);
        mTabLayout.setCurrentTab(mTabLayout.getCurrentTab());
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (titles == null) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            //  UiUtils.startActivity(NewsSearchActivity.class);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}