package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/10.
 */

public class IntegralRuleChildAdapter extends BaseAdapter<String> {


    public IntegralRuleChildAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralRuleChildHolder(parent, R.layout.item_integral_child);
    }

    class IntegralRuleChildHolder extends BaseViewHolder<String> {
        @BindView(R.id.tv_integral_child)
        TextView mTvIntegralChild;
        public IntegralRuleChildHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(String data) {
            mTvIntegralChild.setText(data);
        }
    }

}
