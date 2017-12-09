package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/7/6.
 */

public class ReturnGoodsDetail {
    @SerializedName("ret_info")
    public RetInfo retInfo;
    @SerializedName("sales_info")
    public SalesInfo salesInfo;
    @SerializedName("ret_detail_list")
    public List<RetDetailList> retDetailList;

    public class RetInfo {
        @SerializedName("ret_sn")
        public String retSn;
        @SerializedName("ret_amount")
        public String retAmount;
        @SerializedName("pay_money")
        public String payMoney;
        @SerializedName("exempt_money")
        public String exemptMoney;
        @SerializedName("ret_time")
        public String retTime;
    }

    public class SalesInfo {
        @SerializedName("order_sn")
        public String orderSn;
        @SerializedName("order_amount")
        public String orderAmount;
        @SerializedName("add_time")
        public String addTime;
    }

}
