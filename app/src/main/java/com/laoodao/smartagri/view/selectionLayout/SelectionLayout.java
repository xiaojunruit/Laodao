package com.laoodao.smartagri.view.selectionLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欧源 on 2017/4/18.
 * 供求：  菜单筛选----->>>分类。地区，发布日期
 */

public class SelectionLayout extends LinearLayout {

    private Context mContext;
    private LinearLayout mParent;
    private OnSelectedListener mOnSelectedListener;

    public void onSelectedListener(OnSelectedListener listener) {
        this.mOnSelectedListener = listener;
    }


    public SelectionLayout(Context context) {
        this(context, null);
    }

    public SelectionLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mParent = this;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private List<SelectionItem> mdata = new ArrayList<>();

    public void setData(List<SelectionItem> data) {
        if (data != null) {
            mdata.clear();
        }
        this.mdata = data;
        if (mdata != null && mdata.size() > 0) {
            for (int i = 0; i < mdata.size(); i++) {
                ChildView childView = new ChildView(mContext);
                LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                childView.setLayoutParams(params);
                childView.setTag(i);
                addView(childView);
            }
        }
    }

    public void setTitle(List<String> title) {
        if (title != null && title.size() > 0) {
            for (int i = 0; i < title.size(); i++) {
                ChildView childView = new ChildView(mContext);
                LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                childView.setLayoutParams(params);
                childView.setTag(i);
                childView.setTitle(title.get(i));
                addView(childView);
            }
        }
    }

    public void setMenuList(int position, List<? extends SelectionItem> data) {
        ChildView childAt = (ChildView) getChildAt(position);
        childAt.setItemList(data);
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public class ChildView extends LinearLayout implements OnClickListener, DialogInterface.OnDismissListener, SelectionDialog.OnSelectedResultListener {

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
            if (position == 1) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, position);
                }
                return;
            }
            //设置选中
            selected();
            if (itemList.size() > 0)
                showSelectionDialog();
        }

        /**
         * 设置当前状态，取反
         */
        public void selected() {
            boolean selected = mLayout.isSelected();
            mLayout.setSelected(!selected);
        }

        /**
         * 显示当前列表
         */
        private void showSelectionDialog() {
            if (mSelectionDialog == null) {
                mSelectionDialog = new SelectionDialog(getContext(), mParent, itemList);
            }
            mSelectionDialog.setOnDismissListener(this);
            mSelectionDialog.setOnSelectedResultListener(this);
            mSelectionDialog.show();
        }


        /*
        dialog销毁的时候，设置选中状态
         */
        @Override
        public void onDismiss(DialogInterface dialog) {
            selected();
        }

        @Override
        public void onSelected(int clickPosition, List<SelectionItem> selectionItem) {
            setTitle(selectionItem.get(clickPosition).getTitle());
            int position = (int) this.getTag();
            mOnSelectedListener.onSelected(clickPosition, position, selectionItem);
        }
    }

    public interface OnSelectedListener<T> {
        void onSelected(int clickPosition, int position, T data);
    }


}
