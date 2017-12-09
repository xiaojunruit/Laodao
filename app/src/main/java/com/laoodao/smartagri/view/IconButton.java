package com.laoodao.smartagri.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;

/**
 * Created by Administrator on 2017/3/21.
 */

public class IconButton extends RelativeLayout {
    private int mIconSize;
    private int mTextSize;
    private int mTextColor;
    private String mTitle;
    private String mMsgCount;
    private int mGap;
    private Drawable mDrawable;
    private TextView mTvTitle, mTvMsgCount;
    private ImageView mIcon;
    private RelativeLayout mContent;

    private Context mContext;

    public IconButton(Context context) {
        this(context, null);
    }

    public IconButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconButton);

        mTextSize = ta.getDimensionPixelSize(R.styleable.IconButton_ib_textSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 14, context.getResources().getDisplayMetrics()));

        mTextColor = ta.getColor(R.styleable.IconButton_ib_textColor, 0xff0099cc);
        mGap = ta.getDimensionPixelSize(R.styleable.IconButton_ib_gap,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));
        mIconSize = ta.getDimensionPixelSize(R.styleable.IconButton_ib_iconSize, 0);
        mTitle = ta.getString(R.styleable.IconButton_ib_title);
        mMsgCount = ta.getString(R.styleable.IconButton_ib_msg_count);
        mDrawable = ta.getDrawable(R.styleable.IconButton_ib_icon_src);
        ta.recycle();
        inflate(context, R.layout.widget_iconbutton, this);

    }


    public void setIcon(String icon) {
        Glide.with(getContext()).load(icon).into(mIcon);
    }

    public ImageView getIcon() {
        return mIcon;
    }

    public void setTvTitle(String title) {
        mTvTitle.setText(title);
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public void setmMsgCount(int count) {
        if (count == 0) {
            mTvMsgCount.setVisibility(View.GONE);
            return;
        }
        mTvMsgCount.setVisibility(View.VISIBLE);
        mTvMsgCount.setText(String.valueOf(count));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvMsgCount = (TextView) findViewById(R.id.tv_msg);
        mIcon = (ImageView) findViewById(R.id.iv_icon);
        mTvTitle.setTextColor(mTextColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mContent = (RelativeLayout) findViewById(R.id.contentPanel);
        if (mDrawable != null) {
            initIcon();
        }
        if (!TextUtils.isEmpty(mMsgCount)) {
            mTvMsgCount.setText(mMsgCount);
            mTvMsgCount.setVisibility(VISIBLE);
        } else {
            mTvMsgCount.setVisibility(GONE);
        }
        mTvTitle.setText(mTitle);
        MarginLayoutParams params = (MarginLayoutParams) mTvTitle.getLayoutParams();
        params.topMargin = mGap;
        mTvTitle.setLayoutParams(params);

    }

    private void initIcon() {
        mIcon.setImageDrawable(mDrawable);
        ViewGroup.LayoutParams params = mIcon.getLayoutParams();
        params.width = mIconSize;
        params.height = mIconSize;
        mIcon.setLayoutParams(params);
        ViewGroup.LayoutParams layoutParams = mContent.getLayoutParams();
        layoutParams.width = mIconSize + dip2px(5);
        layoutParams.height = mIconSize + dip2px(5);
        mContent.setLayoutParams(layoutParams);
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
