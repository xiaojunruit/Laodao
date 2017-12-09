package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/4/24.
 */

public class Farmland {


    public int id;

    @SerializedName("crops_name")
    public String cropName;     //作物名称

    public String acreage;  //面积

    public String latestop;

    public String latestoptime;

    @SerializedName("add_time")
    public String addTime;

    @SerializedName("imgarr")
    public List<String> imageList;


    public String local;


}
