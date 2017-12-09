package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyQA {
    public String id;
    @SerializedName("member_id")
    public String memberId;
    public String title;
    public String content;
    @SerializedName("area_info")
    public String areaInfo;
    @SerializedName("answer_total")
    public String answerTotal;
    @SerializedName("view_total")
    public String viewTotal;
    @SerializedName("know_total")
    public String knowTotal;
    @SerializedName("favorite_total")
    public String favoriteTotal;
    @SerializedName("add_time")
    public String addTime;
    @SerializedName("thumb_img")
    public String thumbImg;
}
