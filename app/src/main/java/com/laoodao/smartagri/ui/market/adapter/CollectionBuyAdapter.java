package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/18.
 */

public class CollectionBuyAdapter extends BaseAdapter<Collection> {
    private Context mContext;

    public CollectionBuyAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyHolder(parent, R.layout.item_buy_tiem);
    }

    class MyHolder extends BaseViewHolder<Collection> {
        @BindView(R.id.list)
        RecyclerView mList;
        @BindView(R.id.tv_group_time)
        TextView mTvGroupTime;
        private BuyChildren mAdapter;
        List<Collection.SupdetailBean> supdetailList;

        public MyHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
            supdetailList = new ArrayList<>();
            mAdapter = new BuyChildren(getContext());
            mList.setLayoutManager(new LinearLayoutManager(getContext()));
            mList.addItemDecoration(new DividerDecoration(ContextCompat.getColor(mContext, R.color.common_divider_narrow), 1, 0, 0));
        }


        @Override
        public void setData(Collection data) {
            super.setData(data);
            mAdapter.addAll(data.supdetail);
            mTvGroupTime.setText(data.date);
            mTvGroupTime.setVisibility(View.GONE);
            mList.setAdapter(mAdapter);
        }

    }
}
