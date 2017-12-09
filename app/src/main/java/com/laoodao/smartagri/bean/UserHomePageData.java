package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/8/15.
 */

public class UserHomePageData {
    @SerializedName("source_content")
    public String sourceContent;
    @SerializedName("nick_name")
    public String nickName;
    @SerializedName("add_time")
    public String addTime;
    public int id;
    @SerializedName("obj_type")
    public int objType;
    @SerializedName("know_total")
    public String knowTotal;
    @SerializedName("view_total")
    public String viewTotal;
    @SerializedName("answer_total")
    public String answerTotal;
    public String img;
    @SerializedName("is_wonder")
    public String isWonder;
    @SerializedName("deal_name")
    public String dealName;
    @SerializedName("obj_id")
    public int objId;
    public String desc;
    public String content;
}
