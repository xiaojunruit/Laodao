package com.laoodao.smartagri.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.UiUtils;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class DrawView extends View {

    private Paint p;
    private Paint paint;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p = new Paint();
        p.setAntiAlias(true);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF oval1 = new RectF(0, 5, getMeasuredWidth(), dip2px(20));
        canvas.drawArc(oval1, 180, 180, false, p);
        RectF oval = new RectF(0, 5, getMeasuredWidth(), dip2px(20));
        canvas.drawArc(oval, 180, 180, false, paint);
        canvas.save();
        canvas.restore();

    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
