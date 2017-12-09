package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class CerifyMenu {
    @SerializedName("cerify_type")
    public List<CerifyTypeBean> cerifyType;
    @SerializedName("special_field")
    public List<SpecialFieldBean> specialField;
    @SerializedName("company_kind")
    public List<CompanyKindBean> companyKind;

    public static class CerifyTypeBean {
        public int id;
        public String name;
    }

    public static class SpecialFieldBean {
        public int id;
        public String name;
        public boolean isSelect;
    }

    public static class CompanyKindBean {
        public int id;
        public String name;
        public boolean isSelect;

    }
}
