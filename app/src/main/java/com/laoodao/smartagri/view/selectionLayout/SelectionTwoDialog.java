package com.laoodao.smartagri.view.selectionLayout;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.TopBaseDialog;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/18.
 * <p>
 * 求购，菜单筛选
 */

public class SelectionTwoDialog extends TopBaseDialog<SelectionTwoDialog> {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.fl_button)
    FrameLayout mFlButton;
    private View mAnchorView;
    private List<SupplyMenu> mData = new ArrayList<>();
    private SelectionAdapter mAdapter;
    private boolean mIsGrid;
    private List<SupplyMenu> separateData = new ArrayList<>();
    private int count = 12;

    public SelectionTwoDialog(Context context, View anchorView) {
        super(context);
        this.mAnchorView = anchorView;
        this.mAdapter = new SelectionAdapter(mContext);
    }

    public SelectionTwoDialog(Context context, View anchorView, List<SupplyMenu> data) {
        super(context);
        this.mAnchorView = anchorView;
        this.mData = data;
        this.mAdapter = new SelectionAdapter(mContext);
    }

    public void setData(List<SupplyMenu> data) {
        this.mData.clear();
        this.separateData.clear();
        mAdapter.clear();
        this.mData.addAll(data);
        dataOperation();
    }

    public void setIsGrid(boolean isGrid) {
        this.mIsGrid = isGrid;
        if (mRecyclerView == null) {
            return;
        }
        isGridOperation();
    }

    @Override
    public View onCreateView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_selected, null);
        KnifeUtil.bindTarget(this, view);
        dataOperation();
        isGridOperation();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void isGridOperation() {
        if (!mIsGrid) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
    }

    private void dataOperation() {
        if (mData != null && mFlButton != null) {
            if (mData.size() > count) {
                for (int i = 0; i < count; i++) {
                    separateData.add(mData.get(i));
                }
                mAdapter.addAll(separateData);
                mFlButton.setVisibility(View.VISIBLE);
            } else {
                mAdapter.addAll(mData);
                mFlButton.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void setUiBeforShow() {
        mAdapter.setOnItemClickListener(position -> {
            List<SupplyMenu> data = mAdapter.getAllData();
            for (int i = 0; i < data.size(); i++) {
                data.get(i).select = (position == i);
            }
            mAdapter.notifyDataSetChanged();
            mOnItemClick.setOnItemClick(position, data.get(position).id, data.get(position).name);
        });
    }

    public OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.mOnItemClick = onItemClick;
    }

    @OnClick(R.id.fl_button)
    public void onClick() {
        mAdapter.clear();
        mFlButton.setVisibility(View.GONE);
        mAdapter.addAll(mData);
        mAdapter.notifyDataSetChanged();
    }

    public interface OnItemClick {
        void setOnItemClick(int position, String id, String title);
    }


    @Override
    protected void onStart() {
        super.onStart();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        int location[] = new int[2];
        final Rect rect = new Rect();

        //获取显示范围，不包括状态栏
        mAnchorView.getWindowVisibleDisplayFrame(rect);
        //获取anchorView 在屏幕的坐标
        mAnchorView.getLocationOnScreen(location);

        int y = location[1] + mAnchorView.getHeight();
        params.dimAmount = 0;
        params.gravity = Gravity.TOP;
        params.x = location[0];
        params.y = y;

        mLlTop.setBackgroundColor(0x80000000);
        mLlTop.setGravity(Gravity.TOP);
        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContext.getResources().getDisplayMetrics().heightPixels - y));

    }

    class SelectionAdapter extends RecyclerArrayAdapter<SupplyMenu> {

        public SelectionAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == R.layout.item_grid_selection) {
                return new GridSelectionHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_grid_selection, parent, false));
            } else {
                return new SelectionHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_selection, parent, false));
            }
        }

        @Override
        public int getViewType(int position) {
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                return R.layout.item_grid_selection;
            } else {
                return R.layout.item_selection;
            }
        }

        class SelectionHolder extends BaseViewHolder<SupplyMenu> {

            TextView tvTitle;

            public SelectionHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView;
            }

            @Override
            public void setData(SupplyMenu data) {
                super.setData(data);
                tvTitle.setText(data.name);
                if (data.select) {
                    tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    tvTitle.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background));
                    Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_hook_blue);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvTitle.setCompoundDrawables(null, null, drawable, null);
                } else {
                    tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.common_h2));
                    tvTitle.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    tvTitle.setCompoundDrawables(null, null, null, null);
                }
            }
        }

        class GridSelectionHolder extends BaseViewHolder<SupplyMenu> {

            private RoundTextView mTvTitle;

            public GridSelectionHolder(View itemView) {
                super(itemView);
                mTvTitle = (RoundTextView) itemView.findViewById(R.id.tv_title);
            }

            @Override
            public void setData(SupplyMenu data) {
                super.setData(data);
                mTvTitle.setText(data.name);
            }
        }
    }

}
