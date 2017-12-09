package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/4/26.
 */

public class TradeRecord {
    public int id;
    @SerializedName("order_sn")
    public String orderSn; //订单号
    @SerializedName("add_time")
    public String addTime; //添加时间
    @SerializedName("order_money")
    public String orderMoney; //交易总额
    public int status; //状态1、已付款，2、未付款
    @SerializedName("pic_prefere")
    public String picPrefere; //优惠
    @SerializedName("pic_discoun")
    public String picDiscoun; //折扣
    @SerializedName("store_name")
    public String storeName; //店铺名称
    @SerializedName("store_id")
    public String storeId;//店铺ID
    @SerializedName("store_chief")
    public String storeChief; //店铺负责人
    @SerializedName("store_phone")
    public String storePhone; //联系电话
    @SerializedName("surplus_money")
    public String surplusMoney;
    public boolean boolOpen;
    public List<Detail> detail;


    public class Detail {
        public String name;
        public String num;
        public double price;
        @SerializedName("total_price")
        public String totalPrice;
    }
}
