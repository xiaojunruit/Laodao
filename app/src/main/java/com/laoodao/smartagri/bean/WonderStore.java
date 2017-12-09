package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class WonderStore {

    public String img;
    public String name;
    @SerializedName("STORE_ID")
    public String StoreId;
    @SerializedName("STORE_CODE")
    public String storeCode;
    @SerializedName("is_wonder")
    public int isWonder;
    public List<String> products;


}
