package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.view.IconButton;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/25.
 */

public class ChoiceOperationAdapter extends BaseAdapter<RecordClassification> {

    public ChoiceOperationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChoiceOperationItemHolder(parent, R.layout.item_choice_operation);
    }

    class ChoiceOperationItemHolder extends BaseViewHolder<RecordClassification> {
        @BindView(R.id.ib_operation)
        IconButton mIbOperation;
        public ChoiceOperationItemHolder(ViewGroup parent, @LayoutRes int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(RecordClassification data) {
            mIbOperation.setIcon(data.icon);
            mIbOperation.setTvTitle(data.title);
        }
    }

}
