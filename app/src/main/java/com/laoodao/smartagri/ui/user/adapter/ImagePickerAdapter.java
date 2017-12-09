package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/23.
 */

public class ImagePickerAdapter extends RecyclerArrayAdapter<LocalMedia> {
    private int maxSelectNum = 3;

    public ImagePickerAdapter(Context context, int maxSelectNum) {
        super(context);
        this.maxSelectNum = maxSelectNum;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(parent);
    }

    static class ImageHolder extends BaseViewHolder<LocalMedia> {

        @BindView(R.id.iv_img)
        ImageView mIvImg;

        public ImageHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_image);
        }



        @Override
        public void setData(LocalMedia data) {

            Glide.with(getContext().getApplicationContext())
                    .load(data.getPath())
                    .thumbnail(0.7f)
                    .into(mIvImg);
        }
    }

}
