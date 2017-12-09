package com.laoodao.smartagri.bean.cotton;

import java.io.Serializable;

/**
 * Created by WORK on 2017/6/29.
 */

public class CottonJsxxList implements Serializable {
    //     "cottonType": "手摘细绒棉",
//             "enterpriseCode": "65148",
//             "firmName": "喀什牌楼农场有限责任公司轧花厂",
//             "firstuptime": "2016-12-01 13:13:29",
//             "ginturnout": "36",
//             "grossWeigh": "4490",
//             "netWeigh": "2270",
//             "seedState": "2",
//             "tareWeigh": "2220",
//             "upTime": "2016-12-04 12:09:39",
//             "weighDate": "2016-12-01"
    public String cottonType;
    public String enterpriseCode;
    public String firmName;
    public String firstuptime;
    public String ginturnout;
    public int grossWeigh;
    public int netWeigh;
    public String seedState;
    public int tareWeigh;
    public String upTime;
    public String weighDate;

    public String state() {
        String str="";
        switch (seedState) {
            case "0":
                str="作废";
                break;
            case "1":
                str="企业验证未通过";
                break;
            case "2":
                str="正常";
                break;
            case "4":
                str="发票冲红";
                break;
            case "5":
                str="发票作废";
                break;
            case "6":
                str="暂未公示";
                break;
        }
        return str;
    }
}
