package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.PesticideDetail;
import com.laoodao.smartagri.bean.Pos;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/17.
 */

public class ConstituteAdapter extends BaseAdapter<List<String>> {


    public ConstituteAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConstituteHolder(parent, R.layout.item_constitute);
    }

    class ConstituteHolder extends BaseViewHolder<List<String>> {
        @BindView(R.id.tv_component)
        TextView mTvComponent;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public ConstituteHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(List<String> data) {
            super.setData(data);
            mTvComponent.setText(data.get(0));
            mTvContent.setText(data.get(1));
        }

    }

}
