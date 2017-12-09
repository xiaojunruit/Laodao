package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/3.
 */

public class ImagePickerAdapter extends BaseAdapter<LocalMedia> {

    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_ADD = 2;

    public int maxSize = 6;


    public ImagePickerAdapter(Context context) {
        super(context);
        addAll(new ArrayList<LocalMedia>());
    }

    @Override
    public void addAll(Collection<? extends LocalMedia> collection) {
        super.addAll(collection);
        if (collection.size() < 6) {
            add(new LocalMedia(null));
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            return new ImagePickerHolder(parent);
        }
        return new AddHolder(parent);
    }

    @Override
    public int getItemCount() {
        int itemCount = getAllData().size();
        if (itemCount == 7) {
            return itemCount - 1;
        }
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(getItem(position).getPath())) {
            return TYPE_IMAGE;
        }
        return TYPE_ADD;
    }

    class ImagePickerHolder extends BaseViewHolder<LocalMedia> {

        @BindView(R.id.iv_img)
        ImageView mIvImg;

        public ImagePickerHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_farmland_image);
        }

        @Override
        public void setData(LocalMedia data) {
            Glide.with(getContext().getApplicationContext())
                    .load(data.getPath())
                    .dontAnimate()
                    .error(R.mipmap.bg_seize_seat)
                    .centerCrop()
                    .into(mIvImg);
        }
    }

    class AddHolder extends BaseViewHolder<Object> {

        @BindView(R.id.iv_img)
        ImageView mIvImg;

        public AddHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_farmland_image);
        }

        @Override
        public void setData(Object data) {
            itemView.setOnClickListener(v -> {
                if (mOnAddImageClickListener != null)
                    mOnAddImageClickListener.onAdd();
            });
        }
    }

    private OnAddImageClickListener mOnAddImageClickListener;

    public void setOnAddImageClickListener(OnAddImageClickListener mOnAddImageClickListener) {
        this.mOnAddImageClickListener = mOnAddImageClickListener;
    }

    public interface OnAddImageClickListener {
        void onAdd();
    }
}
