package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Fertilizer;
import com.laoodao.smartagri.bean.Pesticide;
import com.laoodao.smartagri.bean.TruthQuery;
import com.laoodao.smartagri.ui.discovery.activity.PesticideDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/16.
 */

public class PesticideAdapter extends BaseAdapter<TruthQuery> {


    public PesticideAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new QueryResultHolder(parent, R.layout.item_discovery_query);
    }

    class QueryResultHolder extends BaseViewHolder<TruthQuery> {
        @BindView(R.id.tv_number)
        TextView mTvNumber;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_company)
        TextView mTvCompany;

        public QueryResultHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(TruthQuery data) {
            super.setData(data);
            mTvNumber.setText(data.number);
            mTvType.setText(data.title);
            mTvCompany.setText(data.company);
            itemView.setOnClickListener(v -> {
                PesticideDetailActivity.start(getContext(), data.id);
            });
        }
    }
}
