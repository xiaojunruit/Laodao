package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.cotton.CottonJsxxList;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/6/30.
 */

public class CottonJsxxListAdapter extends BaseAdapter<CottonJsxxList> {
    public CottonJsxxListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CottonJsxxListHolder(parent);
    }


    class CottonJsxxListHolder extends BaseViewHolder<CottonJsxxList> {

        @BindView(R.id.tv_firm_name)
        TextView mTvFirmName;
        @BindView(R.id.tv_net_weigh)
        TextView mTvNetWeigh;
        @BindView(R.id.tv_ginturnout)
        TextView mTvGinturnout;
        @BindView(R.id.tv_cotton_type)
        TextView mTvCottonType;
        @BindView(R.id.tv_weigh_date)
        TextView mTvWeighDate;
        @BindView(R.id.tv_firstuptime)
        TextView mTvFirstuptime;
        @BindView(R.id.tv_upTime)
        TextView mTvUpTime;
        @BindView(R.id.tv_seed_state)
        TextView mTvSeedState;

        public CottonJsxxListHolder(ViewGroup parent) {
            super(parent, R.layout.item_jsxxlist);
        }

        @Override
        public void setData(CottonJsxxList data) {
            super.setData(data);
            getString(mTvFirmName, R.string.firm_name, data.firmName);
            getString(mTvNetWeigh, R.string.net_weigh, data.netWeigh + "");
            getString(mTvGinturnout, R.string.ginturnout, data.ginturnout);
            getString(mTvCottonType, R.string.cotton_type, data.cottonType);
            getString(mTvWeighDate, R.string.weigh_date, data.weighDate);
            if (data.firstuptime == null) {
                mTvFirstuptime.setVisibility(View.GONE);
            } else {
                mTvFirstuptime.setVisibility(View.VISIBLE);
            }
            getString(mTvFirstuptime, R.string.first_up_time, data.firstuptime);
            getString(mTvUpTime, R.string.up_time, data.upTime);
            getString(mTvSeedState, R.string.cotton_state, data.state());
        }

        public void getString(TextView view, int id, String str) {
            view.setText(Html.fromHtml(UiUtils.getString(id, str)));
        }

    }
}
