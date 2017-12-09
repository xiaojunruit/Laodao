package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.ui.discovery.activity.CropDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.NewsDetailActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/18.
 */

public class BaikeAdapter extends BaseAdapter<Baike> {

    public BaikeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaikeHolder(parent, R.layout.item_baike);
    }

    class BaikeHolder extends BaseViewHolder<Baike> {
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;


        public BaikeHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(Baike data) {
            super.setData(data);
            mIvImage.setVisibility(TextUtils.isEmpty(data.image) ? View.GONE : View.VISIBLE);
            Glide.with(getContext()).load(data.image).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.bg_seize_seat).into(mIvImage);
            mTvTitle.setText(data.title);
            mTvContent.setText(data.content);
            itemView.setOnClickListener(v -> {
                CropDetailActivity.start(getContext(), data.id);
            });
        }
    }
}
