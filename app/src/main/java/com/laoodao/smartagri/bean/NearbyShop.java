package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/5/3.
 */

public class NearbyShop {
    public int id;
    public String distance;
    @SerializedName("STORE_ID")
    public String storeId;
    @SerializedName("STORE_CODE")
    public String storeCode;
    @SerializedName("STORE_NAME")
    public String storeName;
    @SerializedName("STORE_CHIEF")
    public String storeChief;
    @SerializedName("STORE_ADDR")
    public String storeAddr;
    @SerializedName("distance_str")
    public String distanceStr;
    public String img;
}
