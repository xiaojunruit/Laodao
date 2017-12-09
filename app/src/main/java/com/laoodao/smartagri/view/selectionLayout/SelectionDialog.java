package com.laoodao.smartagri.view.selectionLayout;

import android.content.Context;
import android.graphics.Rect;
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
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/18.
 * <p>
 * 求购，菜单筛选
 */

public class SelectionDialog extends TopBaseDialog<SelectionDialog> {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private View mAnchorView;
    private List<SelectionItem> mData;
    private SelectionAdapter mAdapter;
    private boolean mIsGrid;
    private OnSelectedResultListener mListener;

    public SelectionDialog(Context context, View anchorView) {
        super(context);
        this.mAnchorView = anchorView;
        this.mAdapter = new SelectionAdapter(mContext);
    }

    public SelectionDialog(Context context, View anchorView, List<SelectionItem> data) {
        super(context);
        this.mAnchorView = anchorView;
        this.mData = data;
        this.mAdapter = new SelectionAdapter(mContext);
    }

    public void setData(List<SelectionItem> data) {
        this.mData = data;
    }

    public void setIsGrid(boolean isGrid) {
        this.mIsGrid = isGrid;
    }

    @Override
    public View onCreateView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_selected, null);
        KnifeUtil.bindTarget(this, view);
        mAdapter.addAll(mData);
        if (!mIsGrid) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        this.mAdapter.setOnItemClickListener(position -> {
            dismiss();
            if (mListener != null) {
                mListener.onSelected(position, mData);

            }
        });
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

    class SelectionAdapter extends RecyclerArrayAdapter<SelectionItem> {

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

        class SelectionHolder extends BaseViewHolder<SelectionItem> {


            public SelectionHolder(View itemView) {
                super(itemView);
            }

            @Override
            public void setData(SelectionItem data) {
                super.setData(data);
                ((TextView) itemView).setText(data.getTitle());
            }
        }

        class GridSelectionHolder extends BaseViewHolder<SelectionItem> {

            private RoundTextView mTvTitle;

            public GridSelectionHolder(View itemView) {
                super(itemView);
                mTvTitle = (RoundTextView) itemView.findViewById(R.id.tv_title);
            }

            @Override
            public void setData(SelectionItem data) {
                super.setData(data);
                mTvTitle.setText(data.getTitle());
            }
        }
    }


    public interface OnSelectedResultListener {
        void onSelected(int clickPosition, List<SelectionItem> selectionItem);
    }

    public void setOnSelectedResultListener(OnSelectedResultListener listener) {
        this.mListener = listener;
    }
}
