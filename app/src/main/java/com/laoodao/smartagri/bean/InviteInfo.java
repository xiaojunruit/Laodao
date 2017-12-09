package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WORK on 2017/4/23.
 */

public class InviteInfo {
    public String desc;
    @SerializedName("desc_long")
    public String descLong;
    @SerializedName("points_desc")
    public String pointsDesc;
    @SerializedName("points_desc_long")
    public String pointsDescLong;
    @SerializedName("invite_num")
    public String inviteNum;
    public String laodao;
    @SerializedName("laodao_name")
    public String laodaoName;
    @SerializedName("share_info")
    public ShareInfo shareInfo;
}
