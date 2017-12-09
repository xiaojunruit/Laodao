package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class Fertilizer {


    public String id;
    public String number;
    @SerializedName("common_name")
    public String commonName;
    public String company;


}
