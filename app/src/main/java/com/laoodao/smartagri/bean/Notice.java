package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/5/4.
 */

public class Notice {
    public int id;
    public String time;
    public String message;
    @SerializedName("source_content")
    public String sourceContent;
    @SerializedName("source_title")
    public String sourceTitle;
    public String title;
    public String url;
    public int state;
    @SerializedName("message_id")
    public int messageId;
}
