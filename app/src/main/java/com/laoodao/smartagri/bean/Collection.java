package com.laoodao.smartagri.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 */

public class Collection {
    public String date;
    public List<SupdetailBean> supdetail;

    public class SupdetailBean {
        public String add_time;
        public String area;
        public String category;
        public String id;
        public String[] thumb;
        public String title;
        public boolean isvalid;

    }

}