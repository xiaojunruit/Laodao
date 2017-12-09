package com.laoodao.smartagri.bean.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ezy on 15-9-29.
 */
public class Pagination<ItemType> {
    public int total;
    @SerializedName("page_size")
    public int size;
    public int page;
    public List<ItemType> items;
    @SerializedName("hasmore")
    public boolean hasMore;
}
