package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/7/6.
 */

public class OrderDetailList {
    @SerializedName("goods_id")
    public int goodsId;             //商品id
    @SerializedName("goods_name")
    public String goodsName;        //商品名称
    @SerializedName("goods_price")
    public double goodsPrice;       //商品价格
    @SerializedName("goods_num")
    public String goodsNum;         //商品数量
    public String units;            //单位
    @SerializedName("total_amount")
    public double totalAmount;      //小计
    @SerializedName("goods_spec")
    public String goodsSpec;
}
