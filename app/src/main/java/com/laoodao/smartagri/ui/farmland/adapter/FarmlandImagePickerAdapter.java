package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/23.
 */

public class FarmlandImagePickerAdapter extends BaseAdapter<LocalMedia> {
    private int maxSelectNum = 3;
    private int width;

    public FarmlandImagePickerAdapter(Context context, int maxSelectNum) {
        super(context);
        this.maxSelectNum = maxSelectNum;
    }

    public FarmlandImagePickerAdapter(Context context, int maxSelectNum, int width) {
        super(context);
        this.maxSelectNum = maxSelectNum;
        this.width = width;
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(parent);
    }

    class ImageHolder extends BaseViewHolder<LocalMedia> {

        @BindView(R.id.iv_img)
        ImageView mIvImg;

        public ImageHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_farmland_image);
        }

        @Override
        public void setData(LocalMedia data) {
            if (width > 0) {
                mIvImg.setLayoutParams(new RelativeLayout.LayoutParams(UiUtils.dip2px(width), UiUtils.dip2px(width)));
            }
            Glide.with(getContext().getApplicationContext())
                    .load(data.getPath())
                    .thumbnail(0.7f)
                    .into(mIvImg);
        }
    }

}
