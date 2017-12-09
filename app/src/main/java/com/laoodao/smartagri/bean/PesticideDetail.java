package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class PesticideDetail {


    public String id;
    public String number;
    public String category;
    @SerializedName("register_name")
    public String registerName;
    @SerializedName("total_content")
    public String totalContent;
    @SerializedName("product_form")
    public String productForm;
    public String toxic;
    @SerializedName("product_name")
    public String productName;
    public String manufacturer;
    public String country;
    @SerializedName("expiry_start")
    public String expiryStart;
    @SerializedName("expiry_end")
    public String expiryEnd;
    @SerializedName("dose_desc")
    public String doseDesc;
    @SerializedName("product_usage")
    public String productUsage;
    public String attention;
    @SerializedName("poisoning_aid")
    public String poisoningAid;
    public String address;
    public String website;
    public String zipcode;
    public String phone;
    public String fax;
    @SerializedName("active_principle")
    public List<List<String>> activePrinciple;
    public List<List<String>> dose;


}
