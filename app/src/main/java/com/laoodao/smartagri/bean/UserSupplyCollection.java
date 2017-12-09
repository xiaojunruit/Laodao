package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class UserSupplyCollection {

    public int id;
    public String title;
    public String category;
    @SerializedName("add_time")
    public String addTime;
    public boolean isvalid;
    public String area;
    public List<String> thumb;


}
