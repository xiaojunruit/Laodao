package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundFrameLayout;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.WonderStore;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class FollowFarmShopAdapter extends BaseAdapter<WonderStore> {
    public interface OnClickListener {
        void setOnClickListener(int positoin, int isWonder);
    }

    public OnClickListener mOnClickListener;

    public FollowFarmShopAdapter(Context context, OnClickListener clickListener) {
        super(context);
        mOnClickListener = clickListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowFarmShopHolder(parent);
    }

    class FollowFarmShopHolder extends BaseViewHolder<WonderStore> {
        @BindView(R.id.riv_img)
        CircleImageView mRivImg;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;
        @BindView(R.id.rfl_follow)
        RoundFrameLayout mRflFollow;
        @BindView(R.id.ll_products)
        LinearLayout mLlProducts;


        public FollowFarmShopHolder(ViewGroup parent) {
            super(parent, R.layout.item_follow_farm_shop);
        }

        @Override
        public void setData(WonderStore data) {
            super.setData(data);
            mLlProducts.removeAllViews();
            Glide.with(getContext()).load(data.img).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.bg_seize_seat).placeholder(R.mipmap.bg_seize_seat).into(mRivImg);
            mTvName.setText(data.name);
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
                NearbyShopDetailActivity.start(getContext(), data.storeCode, data.StoreId);
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(UiUtils.dip2px(5), 0, 0, 0);
            for (int i = 0; i < data.products.size(); i++) {
                RoundTextView textView = new RoundTextView(getContext());
                textView.setPadding(UiUtils.dip2px(8), UiUtils.dip2px(2), UiUtils.dip2px(8), UiUtils.dip2px(2));
                if (i > 1) {
                    textView.setLayoutParams(params);
                }
                textView.setText(data.products.get(i));
                RoundViewDelegate delagate = textView.getDelegate();
                delagate.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_light_blue));
                delagate.setCornerRadius(UiUtils.dip2px(10));
                textView.setTextSize(10);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mLlProducts.addView(textView);
            }


        }

        @OnClick(R.id.rfl_follow)
        public void onClick() {
            mOnClickListener.setOnClickListener(getCurrentPosition(), getItem(getCurrentPosition()).isWonder);
        }
    }
}
