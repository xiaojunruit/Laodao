package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PaymentDetail;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

/**
 * Created by WORK on 2017/5/2.
 */

public class PaymentDetailAdapter extends BaseAdapter<PaymentDetail> {
    public PaymentDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaymentDetailHolder(parent, R.layout.item_payment_detail);
    }

    class PaymentDetailHolder extends BaseViewHolder<PaymentDetail> {

        public PaymentDetailHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }
    }

}
