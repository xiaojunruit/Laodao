package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.LoanRecord;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.utils.UiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 */

public class TransactionRecordChildAdapter extends RecyclerView.Adapter<TransactionRecordChildAdapter.ChildHolder> {
    private TradeRecord.Detail detail;
    private List<TradeRecord.Detail> mData = new ArrayList<>();
    private Context mContext;
    private DecimalFormat df;

    public TransactionRecordChildAdapter(Context context) {
        this.mContext = context;
    }

    public void addAll(List<TradeRecord.Detail> data) {
        if (data != null) {
            this.mData.clear();
        }
        this.mData.addAll(data);
    }

    @Override
    public TransactionRecordChildAdapter.ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChildHolder holder = new ChildHolder(LayoutInflater.from(mContext).inflate(R.layout.item_transaction_record_child, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TransactionRecordChildAdapter.ChildHolder holder, int position) {
        detail = mData.get(position);
        if (df == null) {
            df = new DecimalFormat("#.00");
        }
        holder.mTvName.setText(detail.name);
        holder.mTvNum.setText(UiUtils.getString(R.string.num, detail.num));
        holder.mTvMoney.setText(UiUtils.getString(R.string.price, df.format(detail.price)));
        holder.mTvSubtotal.setText(UiUtils.getString(R.string.subtotal, detail.totalPrice));
    }

    @Override
    public int getItemCount() {

        return mData == null ? 0 : mData.size();
    }

    class ChildHolder extends RecyclerView.ViewHolder {

        TextView mTvSubtotal;
        TextView mTvMoney;
        TextView mTvNum;
        TextView mTvName;


        public ChildHolder(View itemView) {
            super(itemView);
            mTvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            mTvSubtotal = (TextView) itemView.findViewById(R.id.tv_subtotal);
            mTvNum = (TextView) itemView.findViewById(R.id.tv_num);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
