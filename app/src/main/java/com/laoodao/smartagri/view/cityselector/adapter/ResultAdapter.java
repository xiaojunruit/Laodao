package com.laoodao.smartagri.view.cityselector.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.db.Area;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/5/5.
 */

public class ResultAdapter extends BaseAdapter<Area> {


    public ResultAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultHolder(parent);
    }


    class ResultHolder extends BaseViewHolder<Area> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public ResultHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_city);
        }


        @Override
        public void setData(int position, Area data) {
            super.setData(position, data);
            mTvTitle.setText(data.name);
        }
    }


}
