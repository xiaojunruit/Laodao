package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/8/15.
 */

public class UserHomePageHead {
    public int sex;
    public String nick_name;
    @SerializedName("member_type")
    public int memberType;
    @SerializedName("member_avatar")
    public String memberAvatar;
    @SerializedName("is_self")
    public int isSelf;
    @SerializedName("area_info")
    public String areaInfo;
    public String signature;
    @SerializedName("wonder_total")
    public String wonderTotal;
    @SerializedName("fans_total")
    public String fansTotal;
    @SerializedName("wonder_shop")
    public String wonderShop;
    @SerializedName("wonder_price")
    public String wonderPrice;
    @SerializedName("collect_total")
    public String collectTotal;
    @SerializedName("is_wonder")
    public int isWonder;
    public String uid;
    @SerializedName("member_type_name")
    public String memberTypeName;
    public ShareInfo share;
    public String mobile;
}
