package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.bean.SupplyMarketing;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Administrator on 2017/4/23.
 */

public class FarmIncomeAdapter extends RecyclerArrayAdapter<SupplyMarketing> {
    public FarmIncomeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(parent, R.layout.item_farmland) {
            @Override
            public void setData(Object data) {
                super.setData(data);
            }
        };
    }
}
