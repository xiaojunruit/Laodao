package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/3.
 */

public class NewCollectionBuyAdapter extends BaseAdapter<Collection.SupdetailBean> {



    public NewCollectionBuyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(parent, R.layout.item_buy);
    }

    class MyHolder extends BaseViewHolder<Collection.SupdetailBean> {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.iv_isvalid)
        ImageView mIvIsvalid;

        public MyHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(Collection.SupdetailBean data) {
            super.setData(data);
            mTvTime.setText(data.add_time);
            mTvAddress.setText(data.area);
            mTvAddress.setVisibility(data.area.isEmpty() ? View.GONE : View.VISIBLE);
            mTvTitle.setText(data.title);
            mTvType.setText(data.category);
            mIvIsvalid.setVisibility(data.isvalid ? View.GONE : View.VISIBLE);
//            if (data.isvalid) {
//                itemView.setOnClickListener(v -> {
//                    BuyDetailsActivity.start(getContext(), "求购详情", Integer.parseInt(data.id),getCurrentPosition());
//                });
//            }
        }
    }
}
