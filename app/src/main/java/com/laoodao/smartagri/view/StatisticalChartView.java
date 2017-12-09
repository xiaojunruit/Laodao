package com.laoodao.smartagri.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.laoodao.smartagri.R;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class StatisticalChartView extends View {
    private float mXLineStop, mYlineStop, padding, mParentWidth, mParentHeight, paddingRight, paddingTop;
    private float mXlineLength, mYlineLength;
    private Paint paintBlack, paintBlue, paintTitle;
    private float cutLineLength; //x轴分割线长度
    private float CircleRadius;//圆的半径
    private float StartPrice;
    private boolean animationEnd;
    private Path mPath;
    private Paint paintLineBlue;
    private PathMeasure mPathMeasure;
    private PathEffect mEffect;
    private float fraction = 0;
    private ValueAnimator mAnimator;
    private float length;
    private String titile;

    private float[] price;
    private String[] data;
    private float[] pricesToday;

    private float yAverage;
    private float priceYCoordinate;
    private float xAverage;
    private float priceperLength;
    private float priceXAverage;
    private float topBlack, bottomBlack;
    private boolean startCanvasLine;

    private Paint.FontMetrics fontMetricsBlack;


    // private Double[] pricesToday = {5.0, 2.2, 3.3, 2.3, 5.6, 4.5, 3.5};

    public StatisticalChartView(Context context) {
        this(context, null);
    }

    public StatisticalChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticalChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintBlack = new Paint();
        paintBlack.setColor(ContextCompat.getColor(context, R.color.common_h2));
        paintBlack.setAntiAlias(true);
        paintBlack.setTextSize(sp2px(9));
        paintBlack.setTextAlign(Paint.Align.CENTER);
        fontMetricsBlack = paintBlack.getFontMetrics();
        topBlack = fontMetricsBlack.top;
        bottomBlack = fontMetricsBlack.bottom;

        paintTitle = new Paint();
        paintTitle.setColor(ContextCompat.getColor(context, R.color.common_h2));
        paintTitle.setAntiAlias(true);
        paintTitle.setTextSize(sp2px(12));
        paintTitle.setTextAlign(Paint.Align.CENTER);

        paddingRight = dip2px(10);
        padding = dip2px(35);
        paddingTop = dip2px(20);
        cutLineLength = dip2px(7);
        CircleRadius = dip2px(3);
        paintBlue = new Paint();
        paintBlue.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paintBlue.setAntiAlias(true);
        paintBlue.setStyle(Paint.Style.FILL);
        paintBlue.setTextSize(sp2px(16));
        paintLineBlue = new Paint();
        paintLineBlue.setAntiAlias(true);
        paintLineBlue.setStrokeWidth(dip2px(2));
        paintLineBlue.setStyle(Paint.Style.STROKE);
        paintLineBlue.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mPath = new Path();
        mPath.reset();
        mPathMeasure = new PathMeasure();
        mAnimator = ValueAnimator.ofFloat(1, 0);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(2000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fraction = (float) valueAnimator.getAnimatedValue();
                mEffect = new DashPathEffect(new float[]{length, length}, fraction * length);
                paintLineBlue.setPathEffect(mEffect);
                invalidate();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startCanvasLine = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationEnd = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mParentWidth = getMeasuredWidth();
        mParentHeight = getMeasuredHeight();
        mXLineStop = mParentWidth - paddingRight;
        mYlineStop = mParentHeight - padding;
        mXlineLength = mParentWidth - padding - paddingRight;
        mYlineLength = mParentHeight - padding - paddingTop;
        if (price == null || data == null || pricesToday == null) {
            return;
        }
        if (price.length == 0 || data.length == 0) {
            return;
        }
        yAverage = mYlineLength / price.length;
        xAverage = mXlineLength / data.length;
        if (pricesToday.length != 0) {
            priceXAverage = mXlineLength / pricesToday.length;
            priceperLength = (mYlineLength - yAverage) / (price[price.length - 1] - StartPrice);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (price == null || data == null || pricesToday == null) {
            return;
        }
        if (price.length == 0 || data.length == 0 || pricesToday.length == 0) {
            return;
        }
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.white));
        canvas.drawLine(padding, mYlineStop, mXLineStop, mYlineStop, paintBlack);//x轴
        canvas.drawLine(padding, paddingTop, padding, mYlineStop, paintBlack);//y轴
        //左上角箭头
        canvas.drawLine(padding, paddingTop, padding - dip2px(7), paddingTop + dip2px(7), paintBlack);
        canvas.drawLine(padding, paddingTop, padding + dip2px(7), paddingTop + dip2px(7), paintBlack);

        canvas.drawText(titile, mParentWidth / 2, paddingTop, paintTitle);

        for (int i = 0; i < price.length; i++) {
            if (i != 0) {
                canvas.drawLine(padding, yAverage * i + paddingTop, mXLineStop, yAverage * i + paddingTop, paintBlack);
            }
            priceYCoordinate = mYlineStop - (yAverage * i);
            int baseLineY = (int) (priceYCoordinate - topBlack / 2 - bottomBlack / 2);
            canvas.drawText(price[i] + "", padding / 2, baseLineY, paintBlack);
        }

        for (int i = 0; i < data.length; i++) {
            float dataXCoordinate = xAverage * (i + 1) + padding; //日期x轴结束开始坐标
            canvas.drawLine(dataXCoordinate, mYlineStop, dataXCoordinate, mYlineStop - cutLineLength, paintBlack);
            int baseLineY = (int) ((mParentHeight - padding / 2) - topBlack / 2 - bottomBlack / 2);
            canvas.drawText(data[i] + "", dataXCoordinate - xAverage / 2, baseLineY, paintBlack);
        }

        int isFirst = 0;

        if (pricesToday.length != 0 && startCanvasLine) {
            for (int i = 0; i < pricesToday.length; i++) {
                float PriceXCoordinate;
                float PriceYCoordinate;
                PriceXCoordinate = (priceXAverage * (i + 1) + padding) - priceXAverage / 2;
                PriceYCoordinate = (mYlineStop - priceperLength * (pricesToday[i] - StartPrice));
                if (pricesToday[i] == 0.0) {
                    continue;
                }

                if (isFirst == 0) {
                    isFirst++;
                }

                if (isFirst == 1) {
                    isFirst++;
                    mPath.moveTo(PriceXCoordinate, PriceYCoordinate);
                } else {
                    mPath.lineTo(PriceXCoordinate, PriceYCoordinate);
                }
                if (animationEnd && pricesToday.length <= 7) {
                    canvas.drawCircle(PriceXCoordinate, PriceYCoordinate, CircleRadius, paintBlue);
                    canvas.drawText(pricesToday[i] + "", PriceXCoordinate, PriceYCoordinate - dip2px(12), paintBlack);
                }
                mPathMeasure.setPath(mPath, false);
                length = mPathMeasure.getLength();
                canvas.drawPath(mPath, paintLineBlue);
            }
        }


    }

    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setData(float[] price, String[] data, float[] pricesToday, String titile) {
        this.price = price;
        if (price != null && price.length != 0) {
            StartPrice = price[0];
        }
        this.data = data;
        this.pricesToday = pricesToday;
        this.titile = titile;
        mAnimator.start();
        //postInvalidate();
    }
}


