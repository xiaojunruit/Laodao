package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.RepaymentOrder;
import com.laoodao.smartagri.ui.user.activity.OrderDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/6.
 */

public class RepaymentOrderAdapter extends BaseAdapter<RepaymentOrder.RepayOrder> {
    public RepaymentOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepaymentOrderHolder(parent);
    }

    class RepaymentOrderHolder extends BaseViewHolder<RepaymentOrder.RepayOrder> {

        @BindView(R.id.tv_single_repayment_money)
        TextView mTvSingleRepaymentMoney;
        @BindView(R.id.tv_order_num)
        TextView mTvOrderNum;
        @BindView(R.id.tv_order_total_money)
        TextView mTvOrderTotalMoney;
        @BindView(R.id.tv_repayments_money)
        TextView mTvRepaymentsMoney;
        @BindView(R.id.tv_repayment_money)
        TextView mTvRepaymentMoney;

        public RepaymentOrderHolder(ViewGroup parent) {
            super(parent, R.layout.item_repayment_order);
        }

        @Override
        public void setData(RepaymentOrder.RepayOrder data) {
            super.setData(data);
            mTvSingleRepaymentMoney.setText(UiUtils.getString(R.string.single_repayment_money, data.repaymentMoney));
            mTvOrderNum.setText(UiUtils.getString(R.string.order_num, data.orderSn));
            mTvOrderTotalMoney.setText(UiUtils.getString(R.string.order_total_money_no, data.allMoney));
            mTvRepaymentsMoney.setText(Html.fromHtml(UiUtils.getString(R.string.repayments_money, data.hasbeenRepaymentMoney)));
            mTvRepaymentMoney.setText(Html.fromHtml(UiUtils.getString(R.string.repayment_no,data.remainderMoney)));
            itemView.setOnClickListener(v -> {
                OrderDetailActivity.start(getContext(), data.salesId);
            });

        }
    }
}
