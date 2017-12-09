package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/2.
 */

public class SupplyMy {
    public int id;
    public String category;
    public String title;
    @SerializedName("add_time")
    public String addTime;
    public String[] thumb;
    @SerializedName("area_info")
    public String areaInfo;
    @SerializedName("share_info")
    public ShareInfo shareInfo;
    public String views;

}
