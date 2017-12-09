package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/7/4.
 */

public class MsgDetail {
    public String title;
    public String content;
    public String id;
    @SerializedName("add_time")
    public String addTime;
    public String img;


}
