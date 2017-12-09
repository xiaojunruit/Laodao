package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.OrderDetailList;
import com.laoodao.smartagri.bean.RetDetailList;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.text.DecimalFormat;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/29.
 */

public class RetDetailAdapter extends BaseAdapter<RetDetailList> {


    public RetDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaymentDetailHolder(parent, R.layout.item_order_detail);
    }

    class PaymentDetailHolder extends BaseViewHolder<RetDetailList> {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_company)
        TextView mTvCompany;
        @BindView(R.id.tv_subtotal)
        TextView mTvSubtotal;
        @BindView(R.id.tv_specifications)
        TextView mTvSpecification;

        public PaymentDetailHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(RetDetailList data) {
            DecimalFormat df = new DecimalFormat("#.00");
            mTvName.setText(UiUtils.getString(R.string.goods_name, data.goodsName));
            mTvPrice.setText(UiUtils.getString(R.string.return_goods_single_price, df.format(data.retPrice), data.units));
            mTvCompany.setText(UiUtils.getString(R.string.goods_num, data.retNum, data.units));
            mTvSpecification.setVisibility(TextUtils.isEmpty(data.goodsSpec) ? View.GONE : View.VISIBLE);
            mTvSpecification.setText(UiUtils.getString(R.string.goods_specifications, data.goodsSpec));
            mTvSubtotal.setText(Html.fromHtml(UiUtils.getString(R.string.subtotal_red, df.format(data.retMoney))));
        }
    }

}
