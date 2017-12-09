package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/5/2.
 */

public class MarketSearchAdapter extends BaseAdapter<Supplylists> {
    public MarketSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultHolder(parent);
    }


    class SearchResultHolder extends BaseViewHolder<Supplylists> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public SearchResultHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_search_question_result);
        }

        @Override
        public void setData(Supplylists data) {
            mTvContent.setText(data.content);
            mTvTitle.setText(data.title);

        }
    }
}
