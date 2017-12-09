package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.TruthQuery;
import com.laoodao.smartagri.ui.discovery.activity.FertilizerDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.MicrobialFertilizerDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.PesticideDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.SeedDetailActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/20.
 */

public class TruthSearchAdapter extends BaseAdapter<TruthQuery> {
    public TruthSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TruthSearchHolder(parent, R.layout.item_discovery_query);
    }

    class TruthSearchHolder extends BaseViewHolder<TruthQuery> {
        @BindView(R.id.tv_number)
        TextView mTvNumber;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_company)
        TextView mTvCompany;

        public TruthSearchHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(TruthQuery data) {
            super.setData(data);
            mTvNumber.setText(data.number);
            mTvType.setText(data.title);
            mTvCompany.setText(data.company);
            itemView.setOnClickListener(v -> {
                if (TextUtils.isEmpty(data.tag)) {
                    return;
                }
                if ("pesticide".equals(data.tag)) {
                    PesticideDetailActivity.start(getContext(), data.itemid);
                } else if ("fertilizer".equals(data.tag)) {
                    FertilizerDetailActivity.start(getContext(), data.itemid);
                } else if ("microbial_fertilizer".equals(data.tag)) {
                    MicrobialFertilizerDetailActivity.start(getContext(), data.itemid);
                } else if ("seed".equals(data.tag)) {
                    SeedDetailActivity.start(getContext(), data.itemid);
                }
            });
        }
    }
}
