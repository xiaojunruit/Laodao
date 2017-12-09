package com.laoodao.smartagri.view.comment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * des:评论列表
 * Created by xsf
 * on 2016.07.11:11
 */
public class CommentListView extends LinearLayout {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CommentListView(Context context) {
        this(context, null);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CommentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }


    public void setAdapter(CommentAdapter adapter) {
        adapter.bindListView(this);
    }

    public void setOnItemClick(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClick(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }


    public  interface OnItemClickListener {
         void onItemClick(int position);
    }

    public  interface OnItemLongClickListener {
         void onItemLongClick(int position);
    }
}
