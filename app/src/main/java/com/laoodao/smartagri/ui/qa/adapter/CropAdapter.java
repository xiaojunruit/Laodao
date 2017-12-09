package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.laoodao.smartagri.utils.UiUtils.getString;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class CropAdapter extends BaseAdapter<Plant> {


    public CropAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CropHolder(parent);
    }

    class CropHolder extends BaseViewHolder<Plant> {

        @BindView(R.id.iv_image)
        RoundedImageView mIvImage;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.btn_add)
        ImageView mBtnAdd;

        public CropHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_crop);
        }

        @OnClick(R.id.btn_add)
        public void onClick() {
            if (mOnAddCropClickListener != null) {
                int position = getCurrentPosition();
                Plant plant = getItem(position);
                mOnAddCropClickListener.addCrop(position, plant);
            }
        }

        @Override
        public void setData(Plant data) {
            mTvName.setText(data.name);
            mBtnAdd.setVisibility(data.isSelected ? View.GONE : View.VISIBLE);
            Glide.with(getContext().getApplicationContext()).load(data.image).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvImage);
        }
    }

    public interface OnAddCropClickListener {
        void addCrop(int position, Plant item);
    }

    private OnAddCropClickListener mOnAddCropClickListener;

    public void setOnAddCropClickListener(OnAddCropClickListener onAddCropClickListener) {
        this.mOnAddCropClickListener = onAddCropClickListener;
    }

    /**
     * 取消选中
     */
    public void cancelSelect(int id) {

        for (Plant p : getAllData()) {
            if (p.id == id) {
                p.isSelected = false;
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void selectCrop(int id) {
        for (Plant p : getAllData()) {
            if (p.id == id) {
                p.isSelected = true;
                break;
            }
        }
        notifyDataSetChanged();
    }


}
