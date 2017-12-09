package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Long-PC on 2017/4/13.
 * 积分详情
 */

public class IntegralDetailAdapter extends BaseAdapter<IntegralDetail> {
    public IntegralDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralDetailHolder(parent);

    }

    class IntegralDetailHolder extends BaseViewHolder<IntegralDetail> {
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_points)
        TextView mTvPoints;

        public IntegralDetailHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_integral_detail);
        }

        @Override
        public void setData(IntegralDetail data) {
            super.setData(data);
            mTvTime.setText(data.addTime);
            mTvDesc.setText(data.desc);
            mTvPoints.setText(data.points);
        }
    }

}
