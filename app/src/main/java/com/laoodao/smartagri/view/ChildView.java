package com.laoodao.smartagri.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.view.selectionLayout.SelectionDialog;
import com.laoodao.smartagri.view.selectionLayout.SelectionItem;
import com.laoodao.smartagri.view.selectionLayout.SelectionLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WORK on 2017/8/1.
 */

public class ChildView extends LinearLayout implements View.OnClickListener {

    private LinearLayout mLayout;
    private List<SelectionItem> itemList = new ArrayList<>();
    private TextView mTvTitle;
    private SelectionDialog mSelectionDialog;

    public ChildView(Context context) {
        this(context, null);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout) inflater.inflate(R.layout._widget_selection, this);
        initView();
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public String getTitle() {
        return mTvTitle.getText().toString();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.setOnClickListener(this);
    }

    public void setItemList(List<? extends SelectionItem> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        mOnItemClickListener.onClick(v,position);
        if (position==1){
            return;
        }
    }

    /**
     * 设置当前状态，取反
     */
    public void selected() {
        boolean selected = mLayout.isSelected();
        mLayout.setSelected(!selected);
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
