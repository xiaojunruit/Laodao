package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/3.
 */

public class NewCollectionSalesAdapter extends BaseAdapter<Collection.SupdetailBean> {


    public NewCollectionSalesAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(parent, R.layout.item_sales);
    }

    class MyHolder extends BaseViewHolder<Collection.SupdetailBean> {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.iv_isvalid)
        ImageView mIvIsvalid;
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;

        public MyHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(Collection.SupdetailBean data) {
            super.setData(data);
            if (data.thumb.length != 0) {
                mIvImg.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.thumb[0])
                        .error(R.mipmap.bg_seize_seat)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvImg);
            } else {
                mIvImg.setVisibility(View.GONE);
            }
            mTvAddress.setText(data.area);
            mTvAddress.setVisibility(data.area.isEmpty() ? View.GONE : View.VISIBLE);
            mTvTime.setText(data.add_time);
            mTvTitle.setText(data.title);
            mTvType.setText(data.category);
            mIvIsvalid.setVisibility(data.isvalid ? View.GONE : View.VISIBLE);
//            if (data.isvalid) {
//                itemView.setOnClickListener(v -> {
//                    SupplyDetailsActivity.start(getContext(), "供销详情", Integer.parseInt(data.id), getCurrentPosition());
//                });
//            }
        }
    }
}
