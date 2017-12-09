package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PaymentHistory;
import com.laoodao.smartagri.ui.user.activity.RepaymentOrderActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/6.
 */

public class PaymentHistoryAdapter extends BaseAdapter<PaymentHistory> {
    public PaymentHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaymentHistoryHolder(parent);
    }

    class PaymentHistoryHolder extends BaseViewHolder<PaymentHistory> {

        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;
        @BindView(R.id.tv_payment_total_money)
        TextView mTvPaymentTotalMoney;
        @BindView(R.id.tv_discount)
        TextView mTvDiscount;
        @BindView(R.id.tv_payment_time)
        TextView mTvPaymentTime;

        public PaymentHistoryHolder(ViewGroup parent) {
            super(parent, R.layout.item_payment_history);
        }

        @Override
        public void setData(PaymentHistory data) {
            super.setData(data);
            mTvShopName.setText(data.storeName);
            mTvPaymentTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.pending_payment, data.money)));
            mTvDiscount.setText(Html.fromHtml(UiUtils.getString(R.string.return_goods_exempt_money, data.exemptMoney)));
            mTvPaymentTime.setText(UiUtils.getString(R.string.payment_history_time, data.repaymentDate));
            itemView.setOnClickListener(v -> {
                RepaymentOrderActivity.start(getContext(), data.id);
            });
        }
    }
}
