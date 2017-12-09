package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class BaikeAllMenu {

    @SerializedName("items")
    public List<ItemsBeanAll> itemsAll;


    public static class ItemsBeanAll {

        public String id;
        public String catename;
        public List<ItemsBean> items;

        public static class ItemsBean {

            public String id;
            public String name;
        }
    }
}
