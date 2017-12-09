package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class CottonfieldDetail {


    public DetailBean detail;
    public MonthBean month;
    public WeekBean week;
    public ShareInfo share;


    public static class DetailBean {

        public String cover;
        public String name;
        public String desc;
        public String price;
        @SerializedName("area_name")
        public String areaName;
        public int id;
        @SerializedName("is_wonder")
        public int isWonder;

    }

    public static class MonthBean {
        public String title;
        public float[] yaxis;
        public String[] xaxis;
        public float[] series;


    }

    public static class WeekBean {
        public String title;
        public float[] yaxis;
        public String[] xaxis;
        public float[] series;


    }


}
