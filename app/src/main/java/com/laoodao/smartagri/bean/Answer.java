package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class Answer {
    public int id;

    public int cid;

    @SerializedName("member_avatar")
    public String avator;


    @SerializedName("member_nick")
    public String nickname;

    @SerializedName("my_praise")
    public boolean isPraise;        //是否  赞

    @SerializedName("area_info")
    public String address;

    @SerializedName("add_time")
    public String time;

    public String content;

    @SerializedName("praise_name")
    public String praiseName;

    @SerializedName("praise_num")
    public int praiseNum;

    @SerializedName("comment")
    public List<Comment> commentList = new ArrayList<>();

    @SerializedName("comment_count")
    public int commentCount;

    @SerializedName("member_type_name")
    public String memberTypeName;
    @SerializedName("is_user_wonder")
    public int isUserWonder;


}
