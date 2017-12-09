package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class AskSuccess {
    @SerializedName("ask_id")
    public int askId;
    public String points;
    public String desc;
    @SerializedName("share_info")
    public ShareInfo shareInfo;
    @SerializedName("is_points_add")
    public boolean isPointsAdd;
}
