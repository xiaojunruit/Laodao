package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class Pesticide {

    public String id;
    public String number;
    @SerializedName("register_name")
    public String registerName;
    @SerializedName("total_content")
    public String totalContent;
    @SerializedName("product_form")
    public String productForm;
    public String manufacturer;


}
