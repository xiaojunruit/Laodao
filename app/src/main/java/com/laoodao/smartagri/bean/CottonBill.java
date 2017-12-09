package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WORK on 2017/7/22.
 */

public class CottonBill implements Serializable {

    public boolean has_data;
    public String year;
    public Now now;
    public ShareInfo shareInfo;
    public FirstBean first;
    @SerializedName("by_company")
    public List<ByCompanyBean> byCompany;

    public class ByCompanyBean implements Serializable {
        public String average_gin;
        public String com_net_weigh;
        public String producer_name;
        public String sell_count;
    }

    public class FirstBean implements Serializable {
        public String gin_turnout;
        public String net_weigh;
        public String per_year;
        public String producer_name;
        public String sell_count;
        public String weight_date;
        public String weight_total;
        public String type;
    }

    public class Now implements Serializable {
        public String average_gin;
        public String early_gin_turnout;
        public String early_weight_date;
        public String last_gin_turnout;
        public String last_weight_date;
        public String sell_count;
        public String top_gin_turnout;
        public String top_net_weigh;
        public String top_weight_date;
        public String total_company_num;
        public String weight_total;
    }
}
