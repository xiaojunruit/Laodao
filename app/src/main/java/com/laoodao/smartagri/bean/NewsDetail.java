package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/5/17.
 */

public class NewsDetail {
    @SerializedName("add_time")
    public String addTime;
    public String content;
    public int id;
    public String source;
    public String title;
    @SerializedName("share_info")
    public ShareInfo shareInfo;
    public List<News> items;

//    public class NewsDetailTj {
//        @SerializedName("add_time")
//        public String addTime;
//        public int id;
//        public List<String> imgArr;
//        public String name;
//        public String type;
//        @SerializedName("view_count")
//        public String viewCount;
//    }
}
