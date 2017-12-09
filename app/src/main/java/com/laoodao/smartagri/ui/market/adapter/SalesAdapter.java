package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.qa.adapter.ImagePreviewActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/18.
 */

public class SalesAdapter extends BaseAdapter<Supplylists> {


    public SalesAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(parent, R.layout.item_sales);


    }


    class MyHolder extends BaseViewHolder<Supplylists> {
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

        public MyHolder(ViewGroup parent, @LayoutRes int viewTyp) {
            super(parent, viewTyp);
        }

        @OnClick(R.id.iv_img)
        public void onClick() {
            Supplylists item = getItem(getCurrentPosition());
            List<String> imgList = new ArrayList<>();
            for (int i = 0; i < item.thumb.length; i++) {
                imgList.add(item.thumb[i]);
            }
            ImagePreviewActivity.start(getContext(), imgList, 0);

        }

        @Override
        public void setData(Supplylists data) {
            mTvTitle.setText(data.title);
            mTvType.setText(data.category);
            mTvTime.setText(data.addTime);
            mTvAddress.setText(data.areaInfo);

            if (data.thumb.length != 0) {
                mIvImg.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.thumb[0])
                        .error(R.mipmap.bg_seize_seat)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate().into(mIvImg);
            } else {
                mIvImg.setVisibility(View.GONE);
            }

            mTvAddress.setVisibility(data.areaInfo.isEmpty() ? View.GONE : View.VISIBLE);
            super.setData(data);
            itemView.setOnClickListener(v -> {
                SupplyDetailsActivity.start(getContext(), "供销详情", data.id);
            });
        }


    }
}
