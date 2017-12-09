package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.laoodao.smartagri.R;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "PinnedSectionDecoration";

    private DecorationCallback callback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;


    public SectionDecoration(Context context, DecorationCallback decorationCallback) {
        Resources res = context.getResources();
        this.callback = decorationCallback;
        paint = new Paint();
        paint.setColor(res.getColor(R.color.background));

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(33);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPaint.setColor(context.getResources().getColor(R.color.common_h3));

        textPaint.getFontMetrics(fontMetrics);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.gap_8x);//40dp


    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        Log.i(TAG, "getItemOffsets：" + pos);
        String groupId = callback.getGroupId(pos);
        // long groupId = callback.getGroupId(pos);
        if (groupId.isEmpty()) return;



        if (pos == 0 || isFirstInGroup(pos)) {//同组的第一个才添加padding
            outRect.top = topGap;
            Log.i(TAG, "padding" + isFirstInGroup(pos));
        } else {
            outRect.top = 0;
        }
    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//            long groupId = callback.getGroupId(position);
//            if (groupId < 0) return;
//            String textLine = callback.getGroupFirstLine(position).toUpperCase();
//            if (position == 0 || isFirstInGroup(position)) {
//                float top = view.getTop() - topGap;
//                float bottom = view.getTop();
//                c.drawRect(left, top, right, bottom, paint);//绘制红色矩形
//                c.drawText(textLine, left, bottom, textPaint);//绘制文本
//            }
//        }
//    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

        //long preGroupId, groupId = -1;
        String preGroupId, groupId = "";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId.isEmpty() || groupId == preGroupId) continue;

            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                String nextGroupId = callback.getGroupId(position + 1);
                // long nextGroupId = callback.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, paint);
            c.drawText(textLine, left + 45, textY - 45, textPaint);
        }

    }


    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = callback.getGroupId(pos - 1);
            String groupId = callback.getGroupId(pos);
//            long prevGroupId = callback.getGroupId(pos - 1);
//            long groupId = callback.getGroupId(pos);
            Log.i(TAG, "groupId：" + groupId + "      " + prevGroupId);
            if (groupId.equals(prevGroupId)) {
                return false;
            }
            return true;
        }
    }

    public interface DecorationCallback {

        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
