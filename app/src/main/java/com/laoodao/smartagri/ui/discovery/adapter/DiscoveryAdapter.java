package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Discover;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopDetailActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/20.
 */

public class DiscoveryAdapter extends BaseAdapter<NearbyShop> {

    public DiscoveryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoveryHolder(parent, R.layout.item_discovery);
    }


    class DiscoveryHolder extends BaseViewHolder<NearbyShop> {

        @BindView(R.id.iv_img)
        RoundedImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_distance)
        TextView mTvDistance;

        public DiscoveryHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(NearbyShop data) {
            Glide.with(getContext()).load(data.img).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.bg_seize_seat).error(R.mipmap.bg_seize_seat).into(mIvImg);
            mTvTitle.setText(data.storeName);
            mTvAddress.setText(data.storeAddr);
            mTvDistance.setText(data.distanceStr);
        }
    }

}
