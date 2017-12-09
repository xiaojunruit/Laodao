package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/7/6.
 */

public class ReturnGoods {
    public int id;
    @SerializedName("store_name")
    public String storeName;
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
