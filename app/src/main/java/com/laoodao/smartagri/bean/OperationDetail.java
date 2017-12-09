package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/4/25.
 */

public class OperationDetail {
    public String artificial;
    public String remark;
    public String time;
    @SerializedName("type_name")
    public String typeName;
    public int type;
    @SerializedName("capital_cost")
    public List<CapitalCost> capitalCost;
    @SerializedName("machinery_cost")
    public List<MachineryCost> machineryCost;
    public List<String> imgarr;

    public class CapitalCost {
        @SerializedName("crops_money")
        public String cropsMoney;
        @SerializedName("crops_name")
        public String cropsName;
    }

    public class MachineryCost {
        public String id;
        @SerializedName("machine_money")
        public String machineMoney;
        @SerializedName("machine_name")
        public String machineName;
    }

}
