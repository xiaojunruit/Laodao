package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class WonderUser {


    public String avatar;
    @SerializedName("member_nick")
    public String membeNick;
    @SerializedName("member_type")
    public int memberType;
    @SerializedName("is_together")
    public int isTogether;
    @SerializedName("wonder_total")
    public String wonderTotal;
    @SerializedName("fans_total")
    public String fansTotal;
    public String signature;
    @SerializedName("member_id")
    public int memberId;
    @SerializedName("is_wonder")
    public int isWonder;


}
