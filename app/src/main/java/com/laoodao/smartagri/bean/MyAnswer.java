package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/4/27.
 */

public class MyAnswer {
    public int id;
    @SerializedName("answerTime")
    public String time;

    @SerializedName("askContent")
    public String askContent;
    public String content;
    @SerializedName("askTitle")
    public String title;
    @SerializedName("askImg")
    public String image;


    @SerializedName("ask_id")
    public int askId;

}
