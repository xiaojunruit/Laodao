package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class AskWonderList {
    public int id;
    public String content;
    @SerializedName("answer_total")
    public String answerTotal;
    @SerializedName("view_total")
    public String viewTotal;
    @SerializedName("know_total")
    public String knowTotal;
    @SerializedName("is_wonder")
    public int isWonder;
    @SerializedName("thumb_img")
    public String thumbImg;


}
