package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.NewDrug;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/26.
 */

public class ManyChildDrugAdapter extends BaseAdapter<NewDrug.TopCenter> {
    public ManyChildDrugAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManyChildDrugHolder(parent);
    }

    class ManyChildDrugHolder extends BaseViewHolder<NewDrug.TopCenter> {

        @BindView(R.id.tv_many_title)
        TextView mTvManyTitle;
        @BindView(R.id.tv_shrink)
        TextView mTvShrink;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.iv_many_icon)
        ImageView mIvManyIcon;

        public ManyChildDrugHolder(ViewGroup parent) {
            super(parent, R.layout.item_many_child_drug);
        }

        @Override
        public void setData(NewDrug.TopCenter data) {
            super.setData(data);
            mTvManyTitle.setText(data.title);
            mTvTitle.setText(data.subtitle);
            mTvContent.setText(Html.fromHtml(data.content));
            Glide.with(getContext()).load(data.icon).into(mIvManyIcon);
            mIvManyIcon.setVisibility(TextUtils.isEmpty(data.icon) ? View.GONE : View.VISIBLE);
        }
    }
}

