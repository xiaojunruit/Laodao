package com.laoodao.smartagri.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.DeviceUtils;

/**
 * Created by 欧源 on 2017/4/19.
 */

public class SettingView extends LinearLayout {

    private String mRightText;
    private String mLeftText;

    private Drawable mLeftIcon;

    private Drawable mRightIcon;


    private TextView mLeftTextView;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private int mLeftIconSize;
    private int mRightIconSize;
    private int mLeftTextSize;
    private int mRightTextSize;
    private int mLeftTextColor;
    private int mRightTextColor;
    private TextView mRightTextView;


    public SettingView(Context context) {
        this(context, null);
    }

    public SettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.SettingView);

        mLeftIcon = attr.getDrawable(R.styleable.SettingView_leftIcon);
        mLeftText = attr.getString(R.styleable.SettingView_leftText);
        mLeftTextSize = attr.getDimensionPixelSize(R.styleable.SettingView_leftTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mRightText = attr.getString(R.styleable.SettingView_rightText);
        mRightTextSize = attr.getDimensionPixelSize(R.styleable.SettingView_rightTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));

        mRightTextColor = attr.getColor(R.styleable.SettingView_rightTextColor, Color.LTGRAY);
        mLeftTextColor = attr.getColor(R.styleable.SettingView_leftTextColor, Color.BLACK);
        mRightIcon = attr.getDrawable(R.styleable.SettingView_rightIcon);
        mLeftIconSize = (int) attr.getDimension(R.styleable.SettingView_leftIconSize, DeviceUtils.dpToPixel(context, 30));
        mRightIconSize = (int) attr.getDimension(R.styleable.SettingView_rightIconSize, DeviceUtils.dpToPixel(context, 18));
        attr.recycle();
        inflate(context, R.layout.widget_setting, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        mLeftImageView = (ImageView) findViewById(R.id.iv_lefticon);
        mLeftTextView = (TextView) findViewById(R.id.tv_lefttext);
        mRightImageView = (ImageView) findViewById(R.id.iv_righticon);
        mRightTextView = (TextView) findViewById(R.id.tv_righttext);


        mLeftTextView.setText(mLeftText);
        mLeftTextView.setTextColor(mLeftTextColor);
        mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
        if (mLeftIcon != null) {
            mLeftImageView.setImageDrawable(mLeftIcon);
            mLeftImageView.setVisibility(VISIBLE);
        }


        mRightTextView.setText(mRightText);
        mRightTextView.setTextColor(mRightTextColor);
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
        if (mRightIcon != null) {
            mRightImageView.setImageDrawable(mRightIcon);
            mRightImageView.setVisibility(VISIBLE);
        }

        ViewGroup.LayoutParams params = mLeftImageView.getLayoutParams();
        params.height = mLeftIconSize;
        params.width = mLeftIconSize;
        mLeftImageView.setLayoutParams(params);

        ViewGroup.LayoutParams params1 = mRightImageView.getLayoutParams();
        params1.height = mRightIconSize;
        params1.width = mRightIconSize;
        mRightImageView.setLayoutParams(params1);

    }
}
