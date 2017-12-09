package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AccountList {
    public int id;
    @SerializedName("operate_icon")
    public String operateIcon;
    @SerializedName("operate_name")
    public String operateName;
    public String remark;
    public String account;
    public String date;
    public String tag;


}
