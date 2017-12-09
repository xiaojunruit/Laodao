package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class BaikeMenu {


    public List<ItemsBean> items;


    public static class ItemsBean {

        public String id;
        public String name;
        @SerializedName("parent_id")
        public String parentId;


    }
}
