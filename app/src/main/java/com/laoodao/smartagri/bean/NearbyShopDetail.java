package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by WORK on 2017/5/3.
 */

public class NearbyShopDetail implements Serializable {
    @SerializedName("STORE_ID")
    public String storeId;
    @SerializedName("STORE_CODE")
    public String storeCode;
    @SerializedName("STORE_NAME")
    public String storeName;
    @SerializedName("STORE_CHIEF")
    public String storeChief;
    @SerializedName("STORE_PHONE")
    public String storePhone;
    @SerializedName("STORE_ADDR")
    public String storeAddr;
    @SerializedName("ZONE_ID")
    public String zoneId;
    @SerializedName("SALE_ZONE_CHIEF")
    public String saleZoneChief;
    @SerializedName("SALE_ZONE_PHONE")
    public String saleZonePhone;
    @SerializedName("DEL_FLAG")
    public String delFlag;
    public String logo;
    public String lng;
    public String lat;
    @SerializedName("distance_str")
    public String distanceStr;
    @SerializedName("area_info")
    public String areaInfo;
    public String products;
    public int certification;
    @SerializedName("is_wonder")
    public int isWonder;
}
