package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 欧源 on 2017/4/28.
 */

public class Comment {

    public int id;
    public int cid;
    @SerializedName("member_nick")
    public String nickname;

    @SerializedName("replay_to")
    public String replayTo;
    public String content;
}
