package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 欧源 on 2017/4/21.
 */

public class News {
    public List<String> imgarr;
    public int id;
    public String title;
    @SerializedName("gc_name")
    public String gcName;
    @SerializedName("add_time")
    public String addTime;
    @SerializedName("view_count")
    public String viewCount;
    public String source;
    public String url;
}
