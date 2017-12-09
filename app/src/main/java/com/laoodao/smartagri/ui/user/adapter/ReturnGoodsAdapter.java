package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.ReturnGoods;
import com.laoodao.smartagri.ui.user.activity.ReturnGoodsDetailActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/6.
 */

public class ReturnGoodsAdapter extends BaseAdapter<ReturnGoods> {
    public ReturnGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReturnGoodsHolder(parent);
    }

    class ReturnGoodsHolder extends BaseViewHolder<ReturnGoods> {

        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;
        @BindView(R.id.tv_order_num)
        TextView mTvOrderNum;
        @BindView(R.id.tv_order_total_money)
        TextView mTvOrderTotalMoney;
        @BindView(R.id.tv_discount)
        TextView mTvDiscount;
        @BindView(R.id.tv_actual_money)
        TextView mTvActualMoney;
        @BindView(R.id.tv_sale_time)
        TextView mTvSaleTime;

        public ReturnGoodsHolder(ViewGroup parent) {
            super(parent, R.layout.item_retrun_goods);
        }

        @Override
        public void setData(ReturnGoods data) {
            super.setData(data);
            mTvShopName.setText(data.storeName);
            mTvOrderNum.setText(UiUtils.getString(R.string.return_goods_sn, data.retSn));
            mTvOrderTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.return_goods_money, data.retAmount)));
            mTvActualMoney.setText(Html.fromHtml(UiUtils.getString(R.string.return_goods_actual_money, data.payMoney)));
            mTvDiscount.setText(Html.fromHtml(UiUtils.getString(R.string.refund_goods_exempt_money, data.exemptMoney)));
            mTvSaleTime.setText(UiUtils.getString(R.string.sale_time, data.retTime));
            itemView.setOnClickListener(v -> {
                ReturnGoodsDetailActivity.start(getContext(), data.id);
            });
        }
    }
}
