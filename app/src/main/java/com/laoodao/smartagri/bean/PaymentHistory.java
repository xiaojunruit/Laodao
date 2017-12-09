package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/4/29.
 */

public class PaymentHistory {
    public int id;
    @SerializedName("store_name")
    public String storeName;
    @SerializedName("store_id")
    public int storeId;
    public String money;
    @SerializedName("exempt_money")
    public String exemptMoney;
    @SerializedName("repayment_date")
    public String repaymentDate;
}
