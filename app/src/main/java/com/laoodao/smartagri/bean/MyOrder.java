package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by WORK on 2017/4/28.
 */

public class MyOrder {
    public int id;
    @SerializedName("store_name")
    public String storeName; //店铺名称
    @SerializedName("order_sn")
    public String orderSn;   //
    @SerializedName("order_amount")
    public String orderAmount;
    @SerializedName("pay_amount")
    public String payAmount;
    @SerializedName("no_pay_amount")
    public String noPayAmount;
    @SerializedName("add_time")
    public String addTime;
    @SerializedName("pic_prefere")
    public String picPrefere;
    @SerializedName("pic_discount")
    public double picDiscount;
    @SerializedName("user_type")
    public String userType;
    public int state;
}
