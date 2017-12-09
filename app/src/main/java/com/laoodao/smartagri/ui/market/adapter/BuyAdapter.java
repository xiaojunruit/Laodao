package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/18.
 */

public class BuyAdapter extends BaseAdapter<Supplylists> {
    public BuyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyHolder(parent, R.layout.item_buy);
    }

    class MyHolder extends BaseViewHolder<Supplylists> {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_address)
        TextView mTvAddress;

        public MyHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }


        @Override
        public void setData(Supplylists data) {
            super.setData(data);
            mTvTitle.setText(data.title);
            mTvType.setText(data.category);
            mTvTime.setText(data.addTime);
            mTvAddress.setText(data.areaInfo);
            mTvAddress.setVisibility(data.areaInfo.isEmpty() ? View.GONE : View.VISIBLE);

            itemView.setOnClickListener(v -> {
                BuyDetailsActivity.start(getContext(), "求购详情", data.id);
            });
        }

    }
}
