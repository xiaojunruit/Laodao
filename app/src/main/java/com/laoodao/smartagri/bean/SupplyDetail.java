package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/28.
 */

public class SupplyDetail {
    public String id;
    public String category;
    public String title;
    @SerializedName("area_info")
    public String areaInfo;
    public String content;
    public int myCollect;
    public String amount;
    public String contactor;
    public String contacmobile;
    @SerializedName("add_time")
    public String addTime;
    public String viewtotal;
    public String price;
    public String topcategory;
    @SerializedName("category_id")
    public String categoryId;
    @SerializedName("share_info")
    public ShareInfo shareInfo;
    public String[] thumb;
    @SerializedName("category_name")
    public String categoryName;
    public String acreage;
    @SerializedName("area_id")
    public String areaId;
    public String uid;
    @SerializedName("member_avatar")
    public String memberAvatar;

    public boolean isCollected() {
        return myCollect == 1;
    }
}
