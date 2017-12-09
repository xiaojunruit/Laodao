package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Menu;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2017/6/28.
 */

public class CottonEnterpriseAdapter extends BaseAdapter<Menu> {
    public CottonEnterpriseAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CottonEnterpriseHolder(parent);
    }

    class CottonEnterpriseHolder extends BaseViewHolder<Menu> {
        public CottonEnterpriseHolder(ViewGroup parent) {
            super(parent, R.layout.item_cotton_enterprise);
        }

        @Override
        public void setData(Menu data) {
            super.setData(data);
        }
    }
}
