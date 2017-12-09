package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WORK on 2017/4/15.
 * 问题
 */

public class Question {
    public int id;

    public String title;

    public String content;

    public int cid;

    //地区
    @SerializedName("area_info")
    public String address;

    //浏览数量
    @SerializedName("view_total")
    public int browse;

    //回答数量
    @SerializedName("answer_total")
    public String answer;

    @SerializedName("add_time")
    public String time;

    //回答的昵称
    @SerializedName("member_name")
    public String nickname;

    //头像
    @SerializedName("member_avatar")
    public String avatar;

    //图片
    @SerializedName("img_url")
    public List<String> imageList;

    //预览图
    @SerializedName("thumb_url")
    public List<String> thumbImageList;

    //想知道答案的人数

    public int wonderNum;
    //1关注了：0：没关注
    public int myWonder;

    //想知道答案的用户
    public String wonderMember;

    public boolean isFollow() {
        return myWonder == 1;
    }


    @SerializedName("share_info")
    public ShareInfo shareInfo;


    public int myCollect;

    public boolean isCollected() {
        return myCollect == 1;
    }

    @SerializedName("comment_total")
    public int commentTotal;
    @SerializedName("member_type_name")
    public String memberTypeName;
    @SerializedName("is_user_wonder")
    public int isUserWonder;
    @SerializedName("show_title")
    public String showTitle;
    @SerializedName("show_content")
    public String showContent;
}
