package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WORK on 2017/5/17.
 */

public class NewDrug {
    public int id;
    public String icon;
    @SerializedName("is_collect")
    public boolean isCollect;
    @SerializedName("top_centent")
    public List<TopCenter> topCentent;
    @SerializedName("top_title")
    public String topTitle;

//    public static class Child {
//        public String content;
//        public String subtitle;
//    }

    public NewDrug(List<TopCenter> topCentent){
        this.topCentent=topCentent;
    }

    public static class TopCenter {
        public String title;
        public String subtitle;
        public String content;
        public String icon;
//        public List<Child> child = new ArrayList<>();
    }
}
