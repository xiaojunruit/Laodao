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

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class BuyChildren extends RecyclerView.Adapter<BuyChildren.ChildHolder> {
    private Context mContext;
    private List<Collection.SupdetailBean> mData = new ArrayList<>();

    public BuyChildren(Context context) {
        this.mContext = context;

    }

    public void addAll(List<Collection.SupdetailBean> data) {
        if (mData != null) {
            mData.clear();
        }
        this.mData.addAll(data);
    }


    @Override
    public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChildHolder holder = new ChildHolder(LayoutInflater.from(mContext).inflate(R.layout.item_buy, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ChildHolder holder, int position) {
        holder.mTvTime.setText(mData.get(position).add_time);
        holder.mTvAddress.setText(mData.get(position).area);
        holder.mTvAddress.setVisibility(mData.get(position).area.isEmpty() ? View.GONE : View.VISIBLE);
        holder.mTvTitle.setText(mData.get(position).title);
        holder.mTvType.setText(mData.get(position).category);
        holder.mIvIsvalid.setVisibility(mData.get(position).isvalid ? View.GONE : View.VISIBLE);
        if (mData.get(position).isvalid) {
            holder.mLlItem.setOnClickListener(v -> {
                BuyDetailsActivity.start(mContext, "求购详情", Integer.parseInt(mData.get(position).id));
            });
        } else {
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    class ChildHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvType;
        TextView mTvTime;
        TextView mTvAddress;
        LinearLayout mLlItem;
        ImageView mIvIsvalid;

        public ChildHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            mLlItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mIvIsvalid = (ImageView) itemView.findViewById(R.id.iv_isvalid);
        }
    }


}
