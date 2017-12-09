package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CategoryAdapter extends BaseAdapter<CateList.CategoryListBean> {


    public CategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(parent, R.layout.item_category);
    }

    class CategoryHolder extends BaseViewHolder<CateList.CategoryListBean> {
        @BindView(R.id.img_icon)
        ImageView mImgIcon;
        @BindView(R.id.tv_type)
        TextView mTvType;

        public CategoryHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(CateList.CategoryListBean data) {
            super.setData(data);
            mTvType.setText(data.name);
            Glide.with(getContext()).load(data.icon).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImgIcon);
            mTvType.setSelected(data.isSelect);
        }
    }
}
