package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CateList implements Serializable {

    @SerializedName("category_list")
    public List<CategoryListBean> categoryList;

    public class CategoryListBean implements Serializable {
        public int id;
        public String name;
        public String icon;
        public boolean isSelect;

    }
}
