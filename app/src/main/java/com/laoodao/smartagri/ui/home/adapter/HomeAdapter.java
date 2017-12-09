package com.laoodao.smartagri.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/22.
 */

public class HomeAdapter extends BaseAdapter<News> {
    private final int TYPE_MULTI_IMAGE = 2;
    private final int TYPE_SINGLE_IMAGE = 1;

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MULTI_IMAGE) {
            return new HomeAdapter.HomeMultiImageHolder(parent);
        }
        return new HomeAdapter.HomeSingleImageHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).imgarr.size() < 2) {
            return TYPE_SINGLE_IMAGE;
        }
        return TYPE_MULTI_IMAGE;
    }

    static class HomeMultiImageHolder extends BaseViewHolder<News> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.content)
        LinearLayout mContent;
        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_tag)
        RoundTextView mTvTag;

        public HomeMultiImageHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_news_multi_image);
        }

        @Override
        public void setData(News data) {
            mTvTitle.setText(data.title);
            mTvFrom.setText(data.source);
            mTvTime.setText(data.addTime);
            mTvTag.setText(data.gcName);
            mTvFrom.setVisibility(TextUtils.isEmpty(data.source) ? View.GONE : View.VISIBLE);
            for (int i = 0; i < data.imgarr.size(); i++) {
                if (i == 3) {
                    return;
                }
                ImageView img = (ImageView) mContent.getChildAt(i);
                img.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.imgarr.get(i)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
            }
        }
    }

    static class HomeSingleImageHolder extends BaseViewHolder<News> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_tag)
        RoundTextView mTvTag;
        @BindView(R.id.iv_image)
        ImageView mIvImage;

        public HomeSingleImageHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_news_single_image);
        }

        @Override
        public void setData(News data) {
            mTvTitle.setText(data.title);
            mTvFrom.setText(data.source);
            mTvTime.setText(data.addTime);
            mTvTag.setText(data.gcName);
            mTvFrom.setVisibility(TextUtils.isEmpty(data.source) ? View.GONE : View.VISIBLE);
            if (data.imgarr.size() > 0) {
                mIvImage.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.imgarr.get(0)).into(mIvImage);
            } else {
                mIvImage.setVisibility(View.GONE);
            }
        }
    }
}
