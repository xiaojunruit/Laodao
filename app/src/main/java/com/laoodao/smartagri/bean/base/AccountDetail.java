package com.laoodao.smartagri.bean.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AccountDetail implements Serializable{
    public String account;
    @SerializedName("account_type")
    public int accountType;
    @SerializedName("operate_type")
    public int operateType;
    public String date;
    public String remark;
    public List<String> imgarr;
    @SerializedName("operate_name")
    public String operateName;
    public String icon;

}
