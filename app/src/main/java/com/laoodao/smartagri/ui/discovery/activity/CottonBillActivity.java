package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CottonBillSlideEvent;
import com.laoodao.smartagri.event.CottonRefreshCode;
import com.laoodao.smartagri.ui.discovery.contract.CottonBillContract;
import com.laoodao.smartagri.ui.discovery.fragment.CottonBillGuideFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CottonFiristBillFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CottonMaxBillFragment;
import com.laoodao.smartagri.ui.discovery.fragment.FavoriteBusinessFragment;
import com.laoodao.smartagri.ui.discovery.fragment.InvitingFriendsFragment;
import com.laoodao.smartagri.ui.discovery.fragment.RecordBillFragment;
import com.laoodao.smartagri.ui.discovery.presenter.CottonBillPresenter;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.CottonBillViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/20.
 */

public class CottonBillActivity extends BaseActivity<CottonBillPresenter> implements CottonBillContract.CottonBillView, ViewPager.OnPageChangeListener {


    @BindView(R.id.viewpager)
    CottonBillViewPager mViewpager;
    @BindView(R.id.guide_ll_point)
    ViewGroup mPointsView;//放置圆点
    private ImageView[] ivPointArray;
    private Fragment[] fragments = {new CottonBillGuideFragment(), new RecordBillFragment(), new CottonFiristBillFragment(), new CottonMaxBillFragment(), new FavoriteBusinessFragment(), new InvitingFriendsFragment()};

    //实例化原点View
    private ImageView mIvPoint;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cotton_bill;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //这里实例化LinearLayout
        //根据ViewPager的item数量实例化数组
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int width = UiUtils.dip2px(10);
        ivPointArray = new ImageView[fragments.length];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        params.rightMargin = width;
        for (int i = 0; i < fragments.length; i++) {
            mIvPoint = new ImageView(this);
            mIvPoint.setLayoutParams(params);
            mIvPoint.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = mIvPoint;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                mIvPoint.setBackgroundResource(R.drawable.shape_indicator_blue_selected);
            } else {
                mIvPoint.setBackgroundResource(R.drawable.shape_indicator_gray_unselect);
            }
            //将数组中的ImageView加入到ViewGroup
            mPointsView.addView(ivPointArray[i]);
        }
    }


    @Override
    protected void configViews() {
        mViewpager.setAdapter(new TabsAdapter(getSupportFragmentManager(), fragments));
        mViewpager.setOnPageChangeListener(this);
        initPoint();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private boolean isBtn;

    @Override
    public void onPageSelected(int position) {
        if (position == 1 && !isBtn) {
            mViewpager.setScrollble(false);
        } else {
            mViewpager.setScrollble(true);
        }
        int length = mPointsView.getChildCount();
        for (int i = 0; i < length; i++) {
            View childAt = mPointsView.getChildAt(i);
            childAt.setBackgroundResource(R.drawable.shape_indicator_gray_unselect);
            if (i == position) {
                childAt.setBackgroundResource(R.drawable.shape_indicator_blue_selected);
            }
        }

        //判断是否是最后一页，若是则显示按钮
//        if (position == mImageResIds.length - 1) {
//            mPointsView.setVisibility(View.GONE);
//        } else {
//            mPointsView.setVisibility(View.VISIBLE);
//        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cottonBillSlideChange(CottonBillSlideEvent event) {
        mViewpager.setCurrentItem(event.position);
        isBtn = true;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new CottonRefreshCode());
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragments[5].onActivityResult(requestCode, resultCode, data);
    }
}
