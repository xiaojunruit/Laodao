package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/4/14.
 * 贷款记录实体类
 */

public class LoanRecord {
    public String name;
    @SerializedName("id_card")
    public String idCard;
    public String phone;
    @SerializedName("total_money")
    public String totalMoney;
    @SerializedName("contract_no")
    public String contractNo;
    public String purpose;
    @SerializedName("start_time")
    public String startTime;
    @SerializedName("expire_time")
    public String expireTime;
    public String account;
    public String bank;
    public String rate;
    @SerializedName("stat_no")
    public String statNo;
    public int state;
    @SerializedName("state_name")
    public String stateName;
    public boolean boolOpen;

}
