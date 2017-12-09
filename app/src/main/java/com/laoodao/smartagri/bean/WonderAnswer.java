package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 欧源 on 2017/5/2.
 */

public class WonderAnswer {

    @SerializedName("add_time")
    public String time;
    public int id;
    public String content;
    @SerializedName("answer_total")
    public int answerCount;

    @SerializedName("view_total")
    public int browse;
    public String title;
    @SerializedName("know_total")
    public int knowTotal;

    @SerializedName("comment_total")
    public int commentTotal;

    @SerializedName("thumb_img")
    public String thumbUrl;
}
