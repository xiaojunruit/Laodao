package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MicrobialFertilizerDetail {

    public String id;
    public String number;
    public String company;
    @SerializedName("common_name")
    public String commonName;
    @SerializedName("product_name")
    public String productName;
    @SerializedName("product_form")
    public String productForm;
    public String qualification;
    public String crop;
    @SerializedName("expiry_date")
    public String expiryDate;

}
