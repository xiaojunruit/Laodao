package com.laoodao.smartagri.ui.home.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.ui.home.adapter.GuidePageAdapter;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欧源 on 2017/5/5.
 */

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int[] mImageResIds;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private ViewGroup mPointsView;//放置圆点
    //实例化原点View
    private ImageView mIvPoint;
    private ImageView[] ivPointArray;

    //最后一页的按钮
    private TextView mTvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_guide);
        mTvStart = (TextView) findViewById(R.id.tv_start);

        mTvStart.setOnClickListener(v -> {
            Global.getInstance().setLastVersionCode(BuildConfig.VERSION_CODE);
            UiUtils.startActivity(MainActivity.class);
            finish();
        });
        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {

            }

            @Override
            public void failure() {
                UiUtils.makeText("请求权限失败,请前往设置开启权限");
            }
        }, new RxPermissions(this));
    }


    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //这里实例化LinearLayout
        mPointsView = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int width = UiUtils.dip2px(10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        params.rightMargin = width;
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            mIvPoint = new ImageView(this);
            mIvPoint.setLayoutParams(params);
            mIvPoint.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = mIvPoint;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                mIvPoint.setBackgroundResource(R.drawable.shape_indicator_selected);
            } else {
                mIvPoint.setBackgroundResource(R.drawable.shape_indicator_unselect);
            }
            //将数组中的ImageView加入到ViewGroup
            mPointsView.addView(ivPointArray[i]);
        }

    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //实例化图片资源
        mImageResIds = new int[]{R.mipmap.ic_guide1, R.mipmap.ic_guide2, R.mipmap.ic_guide3, R.mipmap.ic_splash};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = mImageResIds.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mImageResIds[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        mViewPager.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int length = mPointsView.getChildCount();
        for (int i = 0; i < length; i++) {
            View childAt = mPointsView.getChildAt(i);
            childAt.setBackgroundResource(R.drawable.shape_indicator_unselect);
            if (i == position) {
                childAt.setBackgroundResource(R.drawable.shape_indicator_selected);
            }
        }

        //判断是否是最后一页，若是则显示按钮
        if (position == mImageResIds.length - 1) {
            mPointsView.setVisibility(View.GONE);
            mTvStart.setVisibility(View.VISIBLE);
        } else {
            mPointsView.setVisibility(View.VISIBLE);
            mTvStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
