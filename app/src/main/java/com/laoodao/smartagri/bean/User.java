package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WORK on 2017/4/21.
 */

public class User implements Serializable {
    public static final long serialVersionUID = -3418024125412507001L;
    public String cid;          //用户id
    public String uid;          //用户名
    public String mobile;       //手机号码
    public String avatar;       //用户头像
    public String nickname;     //昵称
    public String points;       //积分
    public String loan;         //贷款
    public String token;        //token
    @SerializedName("is_qq_bind")
    public boolean isQqBind;
    @SerializedName("is_wx_bind")
    public boolean isWxBind;
    @SerializedName("qq_bind_name")
    public String qqBindName;
    @SerializedName("wx_bind_name")
    public String wxBindName;
    @SerializedName("msg_push_state")
    public String msgPushState;
    @SerializedName("follow_plant")
    public List<Plant> followPlant;
    public String integrity;
    @SerializedName("is_show_integrity")
    public boolean isShowIntegrity;
    public String signature;
    @SerializedName("member_type_name")
    public String memberTypeName;
    @SerializedName("member_type")
    public String memberType;
    public String local;
    public String emcode;
    public String truename;
    @SerializedName("member_type_name1")
    public String memberTypeName1;

    @Override
    public String toString() {
        return "User{" +
                "cid='" + cid + '\'' +
                ", uid='" + uid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", points='" + points + '\'' +
                ", loan='" + loan + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String sex;          //性别
    @SerializedName("invite_num")
    public String inviteNum;    //邀请人数


}
