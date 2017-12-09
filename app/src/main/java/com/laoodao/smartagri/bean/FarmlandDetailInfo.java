package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WORK on 2017/4/24.
 */

public class FarmlandDetailInfo implements Serializable{
    public int id;
    @SerializedName("crops_name")
    public String cropsName;
    public String acreage;
    @SerializedName("count_money")
    public String countMoney;
    public String address;
    public String fulladdress;
    public List<String> imgArr;
    public String farmlandDesc;
    @SerializedName("area_id")
    public String areaId;
    @SerializedName("area_name")
    public String areaName;
}
