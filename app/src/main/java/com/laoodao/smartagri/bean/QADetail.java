package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/4/17.
 */

public class QADetail {


    public String id;
    @SerializedName("member_id")
    public String memberId;
    public String title;
    public String content;
    @SerializedName("province_id")
    public String provinceId;
    @SerializedName("city_id")
    public String cityId;
    @SerializedName("answer_total")
    public String answerTotal;
    @SerializedName("view_total")
    public String viewTotal;
    @SerializedName("know_total")
    public String knowTotal;
    @SerializedName("favorite_total")
    public String favoriteTotal;
    @SerializedName("crops_category_id")
    public String cropsCategoryId;
    @SerializedName("crop_category_paths")
    public String cropCategoryPaths;
    @SerializedName("add_time")
    public String addTime;
    public List<String> img;


}

