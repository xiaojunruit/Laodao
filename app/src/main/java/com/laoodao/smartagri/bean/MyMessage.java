package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/5.
 */

public class MyMessage {
    public String id;
    public String time;
    public String notice;
    @SerializedName("notice_type")
    public String noticeType;
    @SerializedName("member_name")
    public String memberName;
    public String num;
    public String img;
    public String title;
    public String content;


}
