package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/5/6.
 */

public class HistoryRecord {
    public int state;
    @SerializedName("order_sn")
    public String orderSn;
    @SerializedName("update_time")
    public String updateTime;
    public List<RepayList> records;

    public class RepayList {
        public String desc;
        @SerializedName("add_time")
        public String time;
    }
}
