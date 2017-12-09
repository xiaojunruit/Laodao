package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.event.CropSelectEvent;
import com.laoodao.smartagri.event.ShareIntentDataEvent;
import com.laoodao.smartagri.ui.discovery.interfaces.OnMoveAndSwipedListener;
import com.laoodao.smartagri.ui.qa.interfaces.OnCropDelListener;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class QASelectedCropAdapter extends BaseAdapter<Plant> {


    public QASelectedCropAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedCropHolder(parent);
    }



    class SelectedCropHolder extends BaseViewHolder<Plant> {

        @BindView(R.id.tv_crop)
        RoundTextView mTvCrop;
        @BindView(R.id.iv_del)
        ImageView mIvDel;

        public SelectedCropHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_qa_selected_crop);
        }

        @Override
        public void setData(Plant data) {
            mIvDel.setVisibility(View.GONE);
            mTvCrop.setText(data.name);
//            itemView.setOnClickListener(v -> {
//                if (getAllData().size() <= 1) {
//                    UiUtils.makeText("至少保留一种作物");
//                    return;
//                }
//                mIvDel.setVisibility(mIvDel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            });
            mIvDel.setVisibility(data.imgIsVisibility == true ? View.VISIBLE : View.GONE);
            mIvDel.setOnClickListener(v -> {
                List<Plant> datas = getAllData();
                datas.remove(getAdapterPosition());
                setNotifyOnChange(false);
                addAll(datas, true);
                setNotifyOnChange(true);
                notifyItemRemoved(getAdapterPosition());
                EventBus.getDefault().post(new CropSelectEvent(datas));
            });
        }

    }

}
