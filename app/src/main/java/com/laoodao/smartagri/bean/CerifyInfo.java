package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class CerifyInfo {
    @SerializedName("true_name")
    public String trueName;
    @SerializedName("cerify_type")
    public int cerifyType;
    public int verify;
    @SerializedName("fail_reason")
    public String failReason;
    @SerializedName("special_field_type")
    public List<Integer> specialFieldType;
    @SerializedName("goodat_crops")
    public List<GoodatCropsBean> goodatCrops;
    @SerializedName("crop_info")
    public List<cropInfoBean> cropInfo;
    @SerializedName("company_kind_type")
    public int companyKindType;
    @SerializedName("trade_pic")
    public List<String> tradePic;
    @SerializedName("iden_card_pic")
    public List<String> idenCardPic;

    public static class GoodatCropsBean {

        public int id;
        public String name;

    }

    public static class cropInfoBean {
        @SerializedName("crops_name")
        public String cropsName;
        @SerializedName("crops_acreage")
        public String cropsAcreage;
    }

}
