package com.laoodao.smartagri.view.viewPagerMenu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.laoodao.smartagri.R;

/**
 * Created by 欧源 on 2017/4/20.
 */

public class ViewPagerMenu extends LinearLayout {
    private final LinearLayout mLayout;
    private ViewPager mViewPager;
    private LinearLayout mIndicator;

    public ViewPagerMenu(Context context) {
        this(context, null);
    }

    public ViewPagerMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout) inflater.inflate(R.layout._widget_selection, this);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (LinearLayout) findViewById(R.id.indicator);
    }
}
