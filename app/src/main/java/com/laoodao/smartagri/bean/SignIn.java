package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 欧源 on 2017/4/27.
 */

public class SignIn {


    public int points;

    @SerializedName("is_sign")
    public boolean isSignIn;

    @SerializedName("sign_days")
    public int signInDays;

    public int today;

    public Calendar calendar;

    public class Calendar {

        @SerializedName("search_year")
        public int year;
        @SerializedName("search_month")
        public int month;

        @SerializedName("sign_logs")
        public List<Integer> logs;

    }

}
