package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MarketImagePickerAdapter extends RecyclerView.Adapter<MarketImagePickerAdapter.MyHolder> {
    private int maxSelectNum = 0;
    private int width;
    private final int TYPE_IMAGE = 2;
    private final int TYPE_FOOT_IMAGE = 1;


    private RecyclerView mRecyclerView;

    private List<LocalMedia> mData = new ArrayList<>();

    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    public MarketImagePickerAdapter(Context Context, int width) {
        this.mContext = Context;
        this.width = width;
    }

    public void addAll(List<LocalMedia> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
//        if (mData != null) {
//            this.mData.clear();
//        }
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new MyHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new MyHolder(VIEW_HEADER);
        } else {
            return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_farmland_image, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
           if (haveHeaderView()) position--;
            //TextView content = (TextView) holder.itemView.findViewById(R.id.item_content);
            // TextView time = (TextView) holder.itemView.findViewById(R.id.item_time);
            // content.setText(mData.get(position));
            //  time.setText("2016-1-1");
           // ImageView mIvImg = (ImageView) holder.itemView.findViewById(R.id.iv_image);
//            if (width > 0) {
//                mIvImg.setLayoutParams(new RelativeLayout.LayoutParams(UiUtils.dip2px(width), UiUtils.dip2px(width)));
//            }
            Glide.with(mContext)
                    .load(mData.get(position).getPath())
                    .thumbnail(0.7f)
                    .into(holder.mIvImg);
        }

    }

    @Override
    public int getItemCount() {
        int count = (mData == null ? 0 : mData.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }


    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        ImageView mIvImg;

        public MyHolder(View itemView) {
            super(itemView);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

}
