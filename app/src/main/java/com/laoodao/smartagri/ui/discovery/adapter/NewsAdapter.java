package com.laoodao.smartagri.ui.discovery.adapter;

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
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.ui.discovery.activity.NewsDetailActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/21.
 */

public class NewsAdapter extends BaseAdapter<News> {

    private final int TYPE_MULTI_IMAGE = 2;
    private final int TYPE_SINGLE_IMAGE = 1;

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MULTI_IMAGE) {
            return new NewsMultiImageHolder(parent);
        }
        return new NewsSingleImageHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).imgarr.size() >= 3) {
            return TYPE_MULTI_IMAGE;
        }
        return TYPE_SINGLE_IMAGE;
    }

    static class NewsSingleImageHolder extends BaseViewHolder<News> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_tag)
        RoundTextView mTvTag;

        public NewsSingleImageHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_news_single_image);
        }

        @Override
        public void setData(int position, News data) {
            mTvTitle.setText(data.title);
            mTvFrom.setText(data.source);
            mTvTime.setText(data.addTime);
            mTvTag.setText(data.gcName);
            mTvFrom.setVisibility(TextUtils.isEmpty(data.source) ? View.GONE : View.VISIBLE);
            if (data.imgarr.size() >= 1) {
                mIvImage.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.imgarr.get(0)).into(mIvImage);
            } else {
                mIvImage.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> {
                NewsDetailActivity.start(getContext(), data.id);
            });
        }
    }

    static class NewsMultiImageHolder extends BaseViewHolder<News> {

        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.content)
        LinearLayout mContent;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_tag)
        RoundTextView mTvTag;

        public NewsMultiImageHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_news_multi_image);
            itemView.setOnClickListener(v -> {
            });
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
            itemView.setOnClickListener(v -> {
                NewsDetailActivity.start(getContext(), data.id);
            });
        }
    }
}
