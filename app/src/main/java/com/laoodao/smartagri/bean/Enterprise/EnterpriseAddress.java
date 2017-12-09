package com.laoodao.smartagri.bean.Enterprise;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WORK on 2017/7/3.
 */

public class EnterpriseAddress {
    @SerializedName("area_value")
    public String areaValue;
    @SerializedName("area_name")
    public String areaName;
    public List<EnterpriseAddressChild> child;

//    public EnterpriseAddress(){
//
//    }
//
//    public EnterpriseAddress(String areaValue, String areaName, List<EnterpriseAddressChild> child) {
//        this.areaValue = areaValue;
//        this.areaName = areaName;
//        this.child = child;
//    }
}
