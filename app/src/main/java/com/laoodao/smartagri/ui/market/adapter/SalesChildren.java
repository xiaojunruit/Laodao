package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.event.MarketEvent;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class SalesChildren extends RecyclerView.Adapter<SalesChildren.ChildHolder> {
    private Context mContext;
    private List<Collection.SupdetailBean> mData = new ArrayList<>();

    public SalesChildren(Context context) {
        this.mContext = context;
        EventBus.getDefault().register(this);
    }

    public void addAll(List<Collection.SupdetailBean> data) {
        if (mData != null) {
            this.mData.clear();
        }
        this.mData.addAll(data);
    }

    @Override
    public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChildHolder holder = new ChildHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sales, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ChildHolder holder, int position) {
        if (mData.get(position).thumb.length != 0) {
            holder.mIvImg.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mData.get(position).thumb[0])
                    .error(R.mipmap.bg_seize_seat)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mIvImg);
        } else {
            holder.mIvImg.setVisibility(View.GONE);
        }
        holder.mTvAddress.setText(mData.get(position).area);
        holder.mTvAddress.setVisibility(mData.get(position).area.isEmpty() ? View.GONE : View.VISIBLE);
        holder.mTvTime.setText(mData.get(position).add_time);
        holder.mTvTitle.setText(mData.get(position).title);
        holder.mTvType.setText(mData.get(position).category);
        holder.mIvIsvalid.setVisibility(mData.get(position).isvalid ? View.GONE : View.VISIBLE);
        if (mData.get(position).isvalid) {
            holder.mLlItem.setOnClickListener(v -> {
                SupplyDetailsActivity.start(mContext, "供销详情", Integer.parseInt(mData.get(position).id), position);
            });
        } else {

        }

    }

    @Subscribe
    public void delCollectionEvent(MarketEvent event) {
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ChildHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        LinearLayout mLlItem;
        @BindView(R.id.iv_isvalid)
        ImageView mIvIsvalid;

        public ChildHolder(View itemView) {
            super(itemView);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_img);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            mLlItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mIvIsvalid = (ImageView) itemView.findViewById(R.id.iv_isvalid);
        }


    }


}
