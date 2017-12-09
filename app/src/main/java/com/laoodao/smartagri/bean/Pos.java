package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 欧源 on 2017/5/8.
 */

public class Pos {
    public PosInfo city;


    public PosInfo province;

    public class PosInfo {
        public int i;

        @SerializedName("n")
        public String name;

        @Override
        public String toString() {
            return "PosInfo{" +
                    "i=" + i +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Pos{" +
                "city=" + city +
                ", province=" + province +
                '}';
    }
}
