package com.laoodao.smartagri.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class TriangleView extends View {
    private Paint p;
    private Path path;
    private Paint paintblue;
    private Paint paint;
    private Paint paintText;
    public String mGinTurnout = "", mNetWeigh = "";
    private float x1, y1, x2, y2, x3, y3;

    public TriangleView(Context context) {
        this(context, null);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p = new Paint();
        p.setDither(true);
        p.setAntiAlias(true);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(2);
        p.setStyle(Paint.Style.STROKE);
        paintblue = new Paint();
        paintblue.setColor(getResources().getColor(R.color.wathet));
        paintblue.setAntiAlias(true);
        paintblue.setStyle(Paint.Style.FILL);
        paint = new Paint();
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paintText = new Paint();
        paintText.setDither(true);
        paintText.setTextSize(sp2px(13));
        paintText.setColor(getResources().getColor(R.color.white));
        x1 = dip2px(47);
        y1 = dip2px(57);
        x2 = dip2px(138);
        y2 = dip2px(182);
        y3 = dip2px(85);
        x3 = dip2px(208);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FirstBill);
//        mGinTurnout = typedArray.getString(R.styleable.FirstBill_ginTurnout);
//        mNetWeigh = typedArray.getString(R.styleable.FirstBill_netWeigh);
//        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();
        canvas.drawPath(path, p);


        canvas.drawCircle(x1, y1, dip2px(23), paintblue);
        canvas.drawCircle(x2, y2, dip2px(38), paintblue);
        canvas.drawCircle(x3, y3, dip2px(31), paintblue);

//
        canvas.drawCircle(x1, y1, dip2px(18), paint);
        canvas.drawCircle(x2, y2, dip2px(33), paint);
        canvas.drawCircle(x3, y3, dip2px(26), paint);
//
        canvas.drawText("NO.1", x1 - dip2px(14), y1 + dip2px(4), paintText);
        canvas.drawText("总净重", x2 - dip2px(20), y2 - dip2px(3), paintText);
        canvas.drawText(mNetWeigh + "kg", x2 - dip2px(7) * (mNetWeigh.length() <= 2 ? mNetWeigh.length() : (mNetWeigh.length() - 2)), y2 + dip2px(10), paintText);
        canvas.drawText("衣份", x3 - dip2px(12), y3 - dip2px(3), paintText);
        canvas.drawText(mGinTurnout, x3 - dip2px(5) * (mGinTurnout.length() + 1) / 2, y3 + dip2px(10), paintText);
//        mNetWeigh.length() == 1 ? 1 : (mNetWeigh.length() - 1)
        canvas.save();
        canvas.restore();

    }

    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setDrawText(String ginTurnout, String netWeigh) {
        this.mGinTurnout = ginTurnout;
        this.mNetWeigh = netWeigh;
        postInvalidate();
    }

}
