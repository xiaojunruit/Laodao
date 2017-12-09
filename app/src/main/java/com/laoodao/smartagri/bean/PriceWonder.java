package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class PriceWonder {
    public int id;
    public String name;
    public String cover;
    public String desc;
    @SerializedName("is_wonder")
    public int isWonder;
    public double price;
    @SerializedName("is_message")
    public int isMessage;
    @SerializedName("area_name")
    public String areaName;
    @SerializedName("add_time")
    public String addTime;


}
