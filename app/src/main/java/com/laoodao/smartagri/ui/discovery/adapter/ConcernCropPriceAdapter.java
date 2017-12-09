package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundFrameLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.ui.discovery.activity.PriceClassActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class ConcernCropPriceAdapter extends BaseAdapter<PriceWonder> {
    public interface OnClickListener {
        void setOnClickListener(int positoin, int isWonder);
    }

    public OnClickListener mOnClickListener;


    public ConcernCropPriceAdapter(Context context, OnClickListener clickListener) {
        super(context);
        mOnClickListener = clickListener;

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConcernCropPriceHolder(parent);
    }

    class ConcernCropPriceHolder extends BaseViewHolder<PriceWonder> {


        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;
        @BindView(R.id.rfl_follow)
        RoundFrameLayout mRflFollow;
        @BindView(R.id.riv_img)
        CircleImageView mRivImg;
        private Drawable drawable;


        public ConcernCropPriceHolder(ViewGroup parent) {
            super(parent, R.layout.item_my_attention_price);
        }

        @Override
        public void setData(PriceWonder data) {
            super.setData(data);
            mTvType.setText(data.name);
            Glide.with(getContext()).load(data.cover).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.bg_seize_seat).error(R.mipmap.bg_seize_seat).into(mRivImg);
            mTvDesc.setText(data.desc);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (TextUtils.isEmpty(data.desc)) {
                params.gravity = Gravity.CENTER_VERTICAL;
                mTvDesc.setVisibility(View.GONE);
            } else {
                params.gravity = Gravity.TOP;
                mTvDesc.setVisibility(View.VISIBLE);
            }
            mTvType.setLayoutParams(params);
            if (data.isWonder == 0) {
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_add);
                drawable.setBounds(0, 0, 45, 45);
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
