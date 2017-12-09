package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/4/18.
 */

public class Ask {
    public int id;

    @SerializedName("thumb_img")
    public String thumbUrl;

    public String title;

    public String content;

    @SerializedName("add_time")
    public String time;

    @SerializedName("answer_total")
    public int answerCount;

    @SerializedName("view_total")
    public int browse;

    @SerializedName("area_info")
    public String address;


}
