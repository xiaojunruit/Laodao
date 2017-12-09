package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.bean.UserSupplyCollection;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.qa.adapter.ImagePreviewActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class SupplyAdapter extends BaseAdapter<UserSupplyCollection> {
    public int mType;

    public SupplyAdapter(Context context, int type) {
        super(context);
        mType = type;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SupplyHolder(parent);
    }

    class SupplyHolder extends BaseViewHolder<UserSupplyCollection> {
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
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;
        @BindView(R.id.iv_isvalid)
        ImageView mIvIsvalid;

        public SupplyHolder(ViewGroup parent) {
            super(parent, R.layout.item_supply);
        }

        @Override
        public void setData(UserSupplyCollection data) {
            super.setData(data);
            mTvTitle.setText(data.title);
            mTvType.setText(data.category);
            mTvTime.setText(data.addTime);
            mTvAddress.setVisibility(TextUtils.isEmpty(data.area) ? View.GONE : View.VISIBLE);
            mTvAddress.setText(data.area);
            mIvIsvalid.setVisibility(data.isvalid ? View.GONE : View.VISIBLE);
            if (data.thumb != null && data.thumb.size() != 0) {
                mIvImg.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.thumb.get(0))
                        .error(R.mipmap.bg_seize_seat)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate().into(mIvImg);
            } else {
                mIvImg.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> {
                if (data.isvalid) {
                    if (mType == 1) {
                        SupplyDetailsActivity.start(getContext(), "供销详情", data.id);
                    } else if (mType == 2) {
                        BuyDetailsActivity.start(getContext(), "求购详情", data.id);
                    }

                }

            });
        }

        @OnClick(R.id.iv_img)
        public void onClick() {
            UserSupplyCollection item = getItem(getCurrentPosition());
            ImagePreviewActivity.start(getContext(), item.thumb, 0);
        }
    }
}
