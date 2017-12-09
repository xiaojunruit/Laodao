package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/2.
 */

public class PendingConfirmOrderAdapter extends BaseAdapter<MyOrder> {



    private CheckClick mCheckClick;
    private String ids = "";
    private int su;
    private int un;

    public PendingConfirmOrderAdapter(Context context) {
        super(context);
    }

    public void setOnCheckClick(CheckClick checkclick) {
        this.mCheckClick = checkclick;
    }

    /**
     * 判断是否全选
     *
     * @param cbState
     */
    public void setCbState(boolean cbState) {
        for (int i = 0; i < getAllData().size(); i++) {
//            getAllData().get(i).isCheck = cbState;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PendingConfirmOrderHolder(parent, R.layout.item_pending_confirm_order);
    }

    class PendingConfirmOrderHolder extends BaseViewHolder<MyOrder> {
        @BindView(R.id.cb_collection)
        CheckBox mCbCollection;
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;
        @BindView(R.id.tv_order_num)
        TextView mTvOrderNum;
        @BindView(R.id.tv_order_total_money)
        TextView mTvOrderTotalMoney;
        @BindView(R.id.tv_actual_money)
        TextView mTvActualMoney;
        @BindView(R.id.tv_pending_repayment)
        TextView mTvPendingRepayment;

        public PendingConfirmOrderHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(MyOrder data) {
            mTvShopName.setText(data.storeName);
            mTvOrderNum.setText(UiUtils.getString(R.string.order_num,data.orderSn));
            mTvOrderTotalMoney.setText(UiUtils.getString(R.string.order_total_money,data.orderAmount));
            mTvPendingRepayment.setText(UiUtils.getString(R.string.repayment,data.noPayAmount));
            mTvActualMoney.setText(UiUtils.getString(R.string.actual_money,data.payAmount));

//            mCbCollection.setChecked(data.isCheck);
            mCbCollection.setOnClickListener(v -> {
//                data.isCheck = mCbCollection.isChecked();
                isCheckAll();
                mCheckClick.OnClick(Double.parseDouble(data.payAmount), mCbCollection.isChecked(), su, un);
            });
        }
    }

    private void isCheckAll() {
        su = 0;
        un = 0;
        for (int i = 0; i < getAllData().size(); i++) {
//            if (getAllData().get(i).isCheck) {
//                su += 1;
//            } else {
//                un += 1;
//            }
        }
    }


    private void addIds(String id, boolean state) {
        if (ids.contains(id) && !state) {
            ids = ids.replace(id + ",", "");
        } else if (!ids.contains(id) && state) {
            ids += id + ",";
        }
    }

    public interface CheckClick {
        void OnClick(double actualMoeny, boolean isCheck, int su, int un);
    }

}
