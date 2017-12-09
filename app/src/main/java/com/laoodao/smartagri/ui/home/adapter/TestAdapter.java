package com.laoodao.smartagri.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 欧源 on 2017/4/12.
 */

public class TestAdapter extends RecyclerArrayAdapter<String> {
    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<String>(parent, R.layout.item_test) {

            @Override
            public void setData(String item) {

            }
        };
    }
}
