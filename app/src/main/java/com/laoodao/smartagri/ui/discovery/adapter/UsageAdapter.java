package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PesticideDetail;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/17.
 */

public class UsageAdapter extends BaseAdapter<List<String>> {


    public UsageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConstituteHolder(parent, R.layout.item_usage);
    }

    class ConstituteHolder extends BaseViewHolder<List<String>> {


        public ConstituteHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @BindView(R.id.tv_crop)
        TextView mTvCrop;
        @BindView(R.id.tv_object)
        TextView mTvObject;
        @BindView(R.id.tv_dosage)
        TextView mTvDosage;
        @BindView(R.id.tv_usage)
        TextView mTvUsage;

        @Override
        public void setData(List<String> data) {
            super.setData(data);
            mTvCrop.setText(data.get(0));
            mTvObject.setText(data.get(1));
            mTvDosage.setText(data.get(2));
            mTvUsage.setText(data.get(3));
        }
    }

}
