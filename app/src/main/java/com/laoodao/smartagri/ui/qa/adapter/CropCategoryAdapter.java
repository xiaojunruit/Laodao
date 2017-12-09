package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Crop;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class CropCategoryAdapter extends BaseAdapter<Crop> implements BaseAdapter.OnItemClickListener {

    public CropCategoryAdapter(Context context) {
        super(context);
        this.setOnItemClickListener(this);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CropCateogryHolder(parent);
    }

    @Override
    public  void onItemClick(int position) {
        setSelected(position);
    }

    class CropCateogryHolder extends BaseViewHolder<Crop> {

        @BindView(R.id.tv_category)
        TextView mTvCategory;

        public CropCateogryHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_category_left);
        }

        @Override
        public void setData(Crop data) {
            mTvCategory.setText(data.name);
            itemView.setBackgroundResource(data.isSelected ? R.color.white : R.color.background);
        }
    }


    public void setSelected(int position) {
        Crop item = getItem(position);
        if(item.isSelected){
            return;
        }
        for (Crop crop : getAllData()) {
            crop.isSelected = false;
        }
        item.isSelected = true;
        notifyDataSetChanged();
        if (mOnItemChangeSelectListener != null)
            mOnItemChangeSelectListener.onChangeSelect(position, item);
    }

    private OnItemChangeSelectListener mOnItemChangeSelectListener;

    public void setOnItemChangeSelectListener(OnItemChangeSelectListener mOnItemChangeSelectListener) {
        this.mOnItemChangeSelectListener = mOnItemChangeSelectListener;
    }

    public interface OnItemChangeSelectListener {
        void onChangeSelect(int position, Crop item);
    }
}
