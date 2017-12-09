package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/23.
 */

public class IncomeRecordAdapter extends BaseAdapter<RecordClassification> {
    public IncomeRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IncomeRecordHolder(parent, R.layout.item_farmland);
    }


    class IncomeRecordHolder extends BaseViewHolder<RecordClassification> {

        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.iv_type)
        ImageView mIvType;
        @BindView(R.id.iv_select)
        ImageView mIvSelect;

        public IncomeRecordHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        @Override
        public void setData(RecordClassification data) {
            super.setData(data);
            mTvType.setText(data.title);
            Glide.with(getContext())
                    .load(data.icon)
                    .error(R.mipmap.bg_seize_seat)
                    .into(mIvType);
            mIvSelect.setVisibility(data.isSelect ? View.VISIBLE : View.GONE);
        }
    }
}

