package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class SelectedCropAdapter extends BaseAdapter<Plant> {
    public SelectedCropAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedCropHolder(parent);
    }

    class SelectedCropHolder extends BaseViewHolder<Plant> {

        @BindView(R.id.tv_crop)
        RoundTextView mTvCrop;

        public SelectedCropHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_selected_crop);
        }

        @Override
        public void setData(Plant data) {
            mTvCrop.setText(data.name);
        }
    }


}
