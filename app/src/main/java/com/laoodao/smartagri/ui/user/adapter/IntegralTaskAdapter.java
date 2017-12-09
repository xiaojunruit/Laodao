package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.IntegralTask;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/26.
 */

public class IntegralTaskAdapter extends BaseAdapter<IntegralTask> {
    public IntegralTaskAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralTaskHolder(parent);
    }

    static class IntegralTaskHolder extends BaseViewHolder<IntegralTask> {

        @BindView(R.id.img_left)
        ImageView mImgLeft;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_points)
        TextView mTvPoints;
        @BindView(R.id.tv_go)
        RoundTextView mTvGo;

        public IntegralTaskHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_integral_task);
        }

        @Override
        public void setData(IntegralTask data) {
            Glide.with(getContext()).load(data.icon).into(mImgLeft);
            mTvTitle.setText(data.title);
            mTvContent.setText(data.content);
            mTvPoints.setText(data.points);
            mTvGo.setVisibility(TextUtils.isEmpty(data.url)? View.GONE:View.VISIBLE);
            mTvGo.setOnClickListener(v -> {
                Route.go(getContext(), data.url);
            });
        }
    }
}
