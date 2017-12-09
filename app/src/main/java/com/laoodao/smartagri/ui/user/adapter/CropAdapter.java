package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import retrofit2.http.Multipart;

/**
 * Created by Administrator on 2017/8/17 0017.
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
        @BindView(R.id.rtv_name)
        RoundTextView mRtvName;

        public CropHolder(ViewGroup parent) {
            super(parent, R.layout.item_goodat_crop);
        }

        @Override
        public void setData(Plant data) {
            super.setData(data);
            mRtvName.setText(data.name);
//            if (getCurrentPosition() % 4 != 0) {
//                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layout.setMargins(UiUtils.dip2px(10), 0, 0, 0);
//                mRtvName.setLayoutParams(layout);
//            }
        }
    }
}
