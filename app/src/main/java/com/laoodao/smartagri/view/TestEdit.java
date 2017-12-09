package com.laoodao.smartagri.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by 欧源 on 2017/5/19.
 */

public class TestEdit extends EditText {
    public TestEdit(Context context) {
        this(context, null);
    }

    public TestEdit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = getPaint();
    }

    private Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineBounds = getLineBounds(0, null);
        canvas.drawText("第三方", getPaddingLeft(), canvas.getClipBounds().top + lineBounds, mPaint);
    }
}
