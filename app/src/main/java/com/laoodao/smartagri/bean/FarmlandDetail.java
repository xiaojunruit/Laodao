package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by WORK on 2017/4/24.
 */

public class FarmlandDetail {
    public String time;
    public String type_name;
    @SerializedName("count_money")
    public String countMoney;
    public int id;
    public String desc;
    public List<String> imgarr;

}
