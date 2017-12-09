package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/7/6.
 */

public class RepaymentOrder {
    public String money;
    @SerializedName("exempt_money")
    public String exemptMoney;
    @SerializedName("repayment_date")
    public String repaymentDate;
    @SerializedName("account_repayment_money")
    public String accountRepaymentMoney;
    @SerializedName("cash_repayment_money")
    public String cashRepaymentMoney;
    @SerializedName("repay_order")
    public List<RepayOrder> repayOrder;

    public class RepayOrder {
        @SerializedName("order_sn")
        public String orderSn;
        @SerializedName("repayment_money")
        public String repaymentMoney;
        @SerializedName("all_money")
        public String allMoney;
        @SerializedName("remainder_money")
        public String remainderMoney;
        @SerializedName("hasbeen_repayment_money")
        public String hasbeenRepaymentMoney;
        @SerializedName("sales_id")
        public int salesId;
    }
}
