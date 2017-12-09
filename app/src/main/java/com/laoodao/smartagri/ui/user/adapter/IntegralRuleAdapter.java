package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.IntegralRule;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/10.
 */

public class IntegralRuleAdapter extends BaseAdapter<IntegralRule> {


    public IntegralRuleAdapter(Context context) {
        super(context);

    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralRuleHolder(parent, R.layout.item_integral_rule);
    }

    class IntegralRuleHolder extends BaseViewHolder<IntegralRule> {
        @BindView(R.id.list)
        RecyclerView mList;
        @BindView(R.id.tv_left_tile)
        TextView mTvLeftTile;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_be_careful)
        TextView mTvBeCareful;
        private IntegralRuleChildAdapter mAdapter;

        public IntegralRuleHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
            mAdapter = new IntegralRuleChildAdapter(getContext());
            mList.setLayoutManager(new LinearLayoutManager(getContext()));
            mList.setAdapter(mAdapter);
        }

        @Override
        public void setData(IntegralRule data) {
            mAdapter.clear();
            mAdapter.addAll(data.content);
            mTvLeftTile.setText(data.type);
            mTvTitle.setText(data.title);
            mTvBeCareful.setText(data.notice);
            mTvBeCareful.setVisibility(TextUtils.isEmpty(data.notice) ? View.GONE : View.VISIBLE);
        }
    }

}
