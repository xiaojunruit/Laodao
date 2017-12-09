package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.ui.user.activity.OrderDetailActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/28.
 */

public class MyOrderAdapter extends BaseAdapter<MyOrder> {


    public MyOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrderHolder(parent, R.layout.item_my_order);
    }

    class MyOrderHolder extends BaseViewHolder<MyOrder> {

        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_order_num)
        TextView mTvOrderNum;
        @BindView(R.id.tv_order_total_money)
        TextView mTvOrderTotalMoney;
        @BindView(R.id.tv_discount)
        TextView mTvDiscount;
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;

        public MyOrderHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(MyOrder data) {
            mTvShopName.setText(data.storeName);
            mTvOrderNum.setText(UiUtils.getString(R.string.order_num, data.orderSn));
            mTvOrderTotalMoney.setText(Html.fromHtml(UiUtils.getString(R.string.order_total_money_and_discount, data.orderAmount)));
            if (data.picDiscount != 0) {
                String text = (int) data.picDiscount + "";
                mTvDiscount.setText(Html.fromHtml(UiUtils.getString(R.string.order_discount_money, data.picDiscount % 1 == 0 ? text : data.picDiscount)));
            } else {
                mTvDiscount.setText(Html.fromHtml(UiUtils.getString(R.string.order_prefere_money, data.picPrefere)));
            }
//            mTvDiscount.setText(Html.fromHtml("0.00".equals(data.picDiscount) ? UiUtils.getString(R.string.order_prefere_money, data.picPrefere) : UiUtils.getString(R.string.order_discount_money, data.picDiscount)));
            switch (data.state) {
                case 1://欠款
                    mTvState.setText("欠款");
                    break;
                case 2://还款中
                    mTvState.setText("还款中");
                    break;
                case 3://已完成
                    mTvState.setText("已完成");
                    break;
                case 4://未付
                    mTvState.setText("未付");
                    break;
                case 5://已付
                    mTvState.setText("已付");
                    break;
            }
            itemView.setOnClickListener(v -> {
                OrderDetailActivity.start(getContext(), data.id);
            });
        }
    }
}
