package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/28.
 */

public class Supplylists {

    public int id;
    public String category;
    public String title;
    @SerializedName("add_time")
    public String addTime;
    public String thumb[];
    @SerializedName("area_info")
    public String areaInfo;
    public String content;
    public int type;
}
