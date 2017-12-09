package com.laoodao.smartagri.ui.discovery.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ejz.multistateview.MultiStateView;
import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.BaikeMenuChangeEvent;
import com.laoodao.smartagri.event.CategoryEvent;
import com.laoodao.smartagri.ui.discovery.contract.EncyclopediaContract;
import com.laoodao.smartagri.ui.discovery.fragment.BaikeArticleFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CategoryFragment;
import com.laoodao.smartagri.ui.discovery.presenter.EncyclopediaPresenter;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EncyclopediaActivity extends BaseActivity<EncyclopediaPresenter> implements EncyclopediaContract.EncyclopediaView {


    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.category_viewpager)
    ViewPager mCategoryViewpager;
    ArrayList<Fragment> mFragments;
    @BindView(R.id.guide_ll_point)
    LinearLayout mGuideLlPoint;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private CateList mCateList;
    private BaikeMenu mBaikeMenu;
    private TabsAdapter myAdapter;
    private int categoryId = 0;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_encyclopedia;
    }

    @Override
    protected void configViews() {
        mFragments = new ArrayList<>();
        mPresenter.requestCateList();
        myAdapter = new TabsAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((BaikeArticleFragment) myAdapter.getItem(position)).setCategoryId(getSelectedCategoryId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int count = 0;


    public int getSelectedCategoryId() {
        if (mCateList != null) {
            for (CateList.CategoryListBean categoryListBean : mCateList.categoryList) {
                if (categoryListBean.isSelect) {
                    return categoryListBean.id;
                }
            }
        }
        return 0;
    }

    private void initTabLayout(CateList list) {
        if (list.categoryList.size() == 0) {
            return;
        }
        if (list.categoryList.size() % 4 == 0) {
            count = list.categoryList.size() / 4;
        } else {
            count = list.categoryList.size() / 4 + 1;
        }
        mGuideLlPoint.setVisibility(list.categoryList.size() <= 4 ? View.GONE : View.VISIBLE);

        // list.categoryList.get(0).isSelect = true;
        for (int i = 0; i < count; i++) {
            mFragments.add(CategoryFragment.newInstance(i, list));
        }
        mCategoryViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mCategoryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int length = mGuideLlPoint.getChildCount();
                for (int i = 0; i < length; i++) {
                    View childAt = mGuideLlPoint.getChildAt(i);
                    childAt.setBackgroundResource(R.drawable.shape_indicator_gray_unselect);
                    if (i == position) {
                        childAt.setBackgroundResource(R.drawable.shape_indicator_blue_selected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initPoint();

    }


    @Override
    public void complete() {

    }

    @OnClick(R.id.iv_add)
    public void onClick() {
        UiUtils.startActivity(FindCropActivity.class);
    }


    @Override
    public void showCateList(CateList cateList) {
        mCateList = cateList;
        initTabLayout(cateList);
    }

    @Override
    public void showMenu(BaikeMenu menus) {
        mBaikeMenu = menus;
        initTab(menus);
    }

    public void initTab(BaikeMenu menus) {
        BaseFragment[] fragments = new BaseFragment[menus.items.size()];
        String title[] = new String[menus.items.size()];
//            if (mCateList == null) {
//                categoryId = 0;
//            } else {
//                categoryId = mCateList.categoryList.size() == 0 ? 0 : mCateList.categoryList.get(0).id;
//            }
        categoryId = getSelectedCategoryId();
        for (int i = 0; i < menus.items.size(); i++) {
            title[i] = menus.items.get(i).name;
            fragments[i] = BaikeArticleFragment.newInstance(Integer.parseInt(menus.items.get(i).id), categoryId, i == mTabLayout.getCurrentTab());
        }
        myAdapter.setData(fragments);
        mTabLayout.setViewPager(mViewPager, title);
        mTabLayout.setCurrentTab(mTabLayout.getCurrentTab());
    }


//    class MyAdapter extends FragmentPagerAdapter {
//        private List<Fragment> fragmentList = new ArrayList<>();
//
//        public void setData(List<Fragment> fragments) {
//            this.fragmentList = fragments;
//            notifyDataSetChanged();
//        }
//
//        public MyAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void BaikeMenuEvent(BaikeMenuChangeEvent event) {
        mPresenter.requestShowMenu(1);
    }


    @Subscribe
    public void onEventMainThread(CategoryEvent event) {
        for (int i = 0; i < mFragments.size(); i++) {
            ((CategoryFragment) mFragments.get(i)).dataChange();
        }
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
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


    class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }

    }

    /**
     * 加载底部圆点
     */
    private ImageView[] ivPointArray;
    private ImageView mIvPoint;

    private void initPoint() {
        ivPointArray = new ImageView[mFragments.size()];
        int width = UiUtils.dip2px(10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        params.rightMargin = width;
        int size = mFragments.size();
        for (int i = 0; i < size; i++) {
            mIvPoint = new ImageView(this);
            mIvPoint.setLayoutParams(params);
            mIvPoint.setPadding(30, 0, 30, 0);
            ivPointArray[i] = mIvPoint;
            if (i == 0) {
                mIvPoint.setBackgroundResource(R.drawable.shape_indicator_blue_selected);
            } else {
                mIvPoint.setBackgroundResource(R.drawable.shape_indicator_gray_unselect);
            }
            mGuideLlPoint.addView(ivPointArray[i]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            UiUtils.startActivity(BaikeSearchActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}