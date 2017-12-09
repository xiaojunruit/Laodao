package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by WORK on 2017/5/6.
 */

public class MyOrderDetail {
    public int id;
    public int state;    //状态
    @SerializedName("state_name")
    public String stateName;          //状态描述
    @SerializedName("order_sn")
    public String orderSn;              //订单号
    @SerializedName("add_time")
    public String addTime;              //下单时间
    @SerializedName("order_amount")
    public String orderAmount;          //订单总额
    @SerializedName("pay_amount")
    public String payAmount;            //实付金额
    @SerializedName("no_pay_amount")
    public String noPayAmount;          //待还款金额
    @SerializedName("debt_amount")
    public String debtAmount;           //剩余欠款金额
    @SerializedName("detail_list")
    public List<OrderDetailList> detailList;
    @SerializedName("repay_list")
    public MyOrderDetail.RepayList repayList;
    @SerializedName("user_type")
    public int userType;
    @SerializedName("have_repayment")
    public String haveRepayment;
    @SerializedName("pay_method")
    public String payMethod;
    public class RepayList{
        public String desc;
        public String time;
    }
}
