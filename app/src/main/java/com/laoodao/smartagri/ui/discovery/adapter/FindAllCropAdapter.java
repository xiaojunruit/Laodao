package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.BaikeAllMenu;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/19.
 */

public class FindAllCropAdapter extends BaseAdapter<BaikeAllMenu.ItemsBeanAll.ItemsBean> {


    public FindAllCropAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FindAllCropHolder(parent, R.layout.item_find_crop);
    }

    class FindAllCropHolder extends BaseViewHolder<BaikeAllMenu.ItemsBeanAll.ItemsBean> {
        @BindView(R.id.tv_type)
        TextView mTvType;

        public FindAllCropHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(BaikeAllMenu.ItemsBeanAll.ItemsBean data) {
            super.setData(data);
            mTvType.setText(data.name);
        }
    }
}
