package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class Plant implements Serializable {

    public int id;

    @SerializedName("parent_id")
    public int parentId;

    public String name;

    @SerializedName("cover")
    public String image;
    public boolean isSelected;
    public boolean imgIsVisibility = false;
}
