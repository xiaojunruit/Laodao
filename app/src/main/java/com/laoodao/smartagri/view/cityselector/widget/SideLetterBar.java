package com.laoodao.smartagri.view.cityselector.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class SideLetterBar extends View {
    private static final String[] b = {"当前", "定位", /*"最近",*/ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int mChoose = -1;
    private Paint mPaint;
    private boolean mShowBg = false;
    private OnLetterChangedListener mOnLetterChangedListener;
    private TextView mOverlay;
    private Handler mHandler;
    private Context mContext;
    private OverlayThread mOverlayThread;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHandler = new Handler();
        mOverlayThread = new OverlayThread();
        mPaint = new Paint();
        mContext = context;
    }

    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            mOverlay.setVisibility(View.GONE);
        }
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideLetterBar(Context context) {
        this(context, null);
    }

    /**
     * 设置悬浮的textview
     *
     * @param mOverlay
     */
    public void setOverlay(TextView mOverlay) {
        this.mOverlay = mOverlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowBg) {
            canvas.drawColor(Color.TRANSPARENT);
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            mPaint.setTextSize(sp2px(mContext, 13));
            mPaint.setColor(Color.parseColor("#8c8c8c"));
            mPaint.setAntiAlias(true);
            if (i == mChoose) {
                mPaint.setColor(Color.parseColor("#8c8c8c"));
            }
            float xPos = width / 2 - mPaint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, mPaint);
            mPaint.reset();
        }

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = mChoose;
        final OnLetterChangedListener listener = mOnLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mShowBg = true;
//                if (oldChoose != c && listener != null) {
//                    if (c >= 0 && c < b.length) {
//                        listener.onLetterChanged(b[c]);
//                        mChoose = c;
//                        invalidate();
//
//                        if (mOverlay != null) {
//                            showOverlay(b[c]);
//                        }
//                    }
//                }
////
////                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        mChoose = c;
//                        invalidate();
                        if (mOverlay != null) {
                            showOverlay(b[c]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mShowBg = false;
                mChoose = -1;
//                invalidate();
                if (mOverlay != null) {
//                    mOverlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    public void showOverlay(String s) {
        mOverlay.setVisibility(VISIBLE);
        mOverlay.setText(s);
        mHandler.removeCallbacks(mOverlayThread);
        mHandler.postDelayed(mOverlayThread, 1000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener mOnLetterChangedListener) {
        this.mOnLetterChangedListener = mOnLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
