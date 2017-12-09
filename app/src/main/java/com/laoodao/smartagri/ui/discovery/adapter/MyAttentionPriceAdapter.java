package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundFrameLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.ui.discovery.activity.PriceClassActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class MyAttentionPriceAdapter extends BaseAdapter<PriceWonder> {
    public interface OnClickListener {
        void setOnClickListener(int positoin, int isWonder);
    }

    public OnClickListener mOnClickListener;

    public MyAttentionPriceAdapter(Context context, OnClickListener clickListener) {
        super(context);
        mOnClickListener = clickListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAttentionPriceHolder(parent);
    }

    class MyAttentionPriceHolder extends BaseViewHolder<PriceWonder> {


        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_data)
        TextView mTvData;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;
        @BindView(R.id.rfl_follow)
        RoundFrameLayout mRflFollow;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.riv_img)
        CircleImageView mRivImg;


        public MyAttentionPriceHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_my_attention_pricen_new);

        }

        @Override
        public void setData(PriceWonder data) {
            super.setData(data);
            mTvType.setText(data.name);
            Glide.with(getContext()).load(data.cover).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.bg_seize_seat).error(R.mipmap.bg_seize_seat).into(mRivImg);
            mTvPrice.setText(Html.fromHtml(UiUtils.getResources().getString(R.string.new_price, data.price + "")));
            mTvDesc.setText(data.desc);
            mTvData.setText(data.addTime);
            mTvAddress.setText(data.areaName);

            if (data.isWonder == 0) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_add);
                drawable.setBounds(0, 0, UiUtils.dip2px(10), UiUtils.dip2px(10));
                mRflFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                mTvFollow.setCompoundDrawables(drawable, null, null, null);
                mTvFollow.setText("关注");
            } else {
                mRflFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.common_h5));
                mTvFollow.setCompoundDrawables(null, null, null, null);
                mTvFollow.setText("已关注");
            }
            itemView.setOnClickListener(v -> {
                PriceClassActivity.start(getContext(), data.id, data.name);

            });
        }

        @OnClick(R.id.rfl_follow)
        public void onClick() {
            mOnClickListener.setOnClickListener(getCurrentPosition(), getItem(getCurrentPosition()).isWonder);
        }
    }
}
