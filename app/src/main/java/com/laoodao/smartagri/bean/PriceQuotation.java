package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by WORK on 2017/7/28.
 */

public class PriceQuotation {
    public ShareInfo shareInfo;
    public int id;
    public String attribute;
    public String price;
    public String name;
    @SerializedName("area_name")
    public String areaName;
    @SerializedName("add_time")
    public String addTime;
    @SerializedName("is_wonder")
    public int isWonder;
}
