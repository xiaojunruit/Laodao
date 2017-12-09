package com.laoodao.smartagri.ui.user.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/15.
 */

public class DividerHolder extends BaseViewHolder<Object> {


    public DividerHolder(View itemView) {
        super(itemView);
    }

    public DividerHolder(ViewGroup parent) {
        super(parent, R.layout.item_divider);
    }
}
