package com.laoodao.smartagri.ui.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.activity.TradeRecordDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/18.
 */

public class TradeRecordAdapter extends BaseAdapter<TradeRecord> {

    public TradeRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactionRecordItemHolder(parent, R.layout.item_transaction_record);
    }

    class TransactionRecordItemHolder extends BaseViewHolder<TradeRecord> {
        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_trade_num)
        TextView mTvTradeNum;
        @BindView(R.id.tv_pay)
        RoundTextView mTvPay;
        @BindView(R.id.tv_state_su)
        TextView mTvStateSu;
        @BindView(R.id.trade)
        LinearLayout mTrade;
        @BindView(R.id.tv_store_name)
        TextView mTvStoreName;
        @BindView(R.id.tv_store_controller)
        TextView mTvStoreController;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_trade_money)
        TextView mTvTradeMoney;
        @BindView(R.id.tv_discount_money)
        TextView mTvDiscountMoney;
        @BindView(R.id.list)
        RecyclerView mList;
        @BindView(R.id.tv_trade_total)
        TextView mTvTradeTotal;
        @BindView(R.id.trade_detail)
        LinearLayout mTradeDetail;
        List<TradeRecord.Detail> loanRecordList;
        private TransactionRecordChildAdapter mAdapter;

        public TransactionRecordItemHolder(ViewGroup parent, @LayoutRes int viewType) {
            super(parent, viewType);
            mList.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new TransactionRecordChildAdapter(getContext());
            loanRecordList = new ArrayList<>();
            mList.setAdapter(mAdapter);

        }

        @Override
        public void setData(TradeRecord data) {
            mAdapter.addAll(data.detail);
            mTradeDetail.setVisibility(data.boolOpen ? View.VISIBLE : View.GONE);
            bindData(mTvMoney, R.string.trade, data.orderMoney);
            bindData(mTvTime, R.string.trade_time, data.addTime);
            bindData(mTvPhone, R.string.contact_phone, data.storePhone);
            bindData(mTvStoreName, R.string.store_name, data.storeName);
            bindData(mTvStoreController, R.string.store_controller, data.storeChief);
            bindData(mTvTradeMoney, R.string.trade_money, data.orderMoney);
            bindData(mTvDiscountMoney, R.string.discount, data.picPrefere);
            bindData(mTvTradeNum, R.string.trade_num, data.orderSn);
            bindData(mTvTradeTotal, R.string.total, data.orderMoney);
            mTvStateSu.setVisibility(data.status == Constant.PAY_SU ? View.VISIBLE : View.GONE);
            mTvPay.setVisibility(data.status == Constant.PAY_UN ? View.VISIBLE : View.GONE);
        }

        private void bindData(TextView view, int stringValue, String value) {
            view.setText(UiUtils.getString(stringValue, value));
        }


        @OnClick({R.id.trade, R.id.tv_pay})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_pay:
                    TradeRecordDetailActivity.start(view.getContext(), getAllData().get(getDataPosition()).id);
                    break;
                case R.id.trade:
                    List<TradeRecord> recordList = getAllData();
                    for (TradeRecord record : recordList) {
                        record.boolOpen = false;
                    }
                    getItem(getDataPosition()).boolOpen = true;
                    notifyDataSetChanged();
                    break;
            }
        }
    }

}
