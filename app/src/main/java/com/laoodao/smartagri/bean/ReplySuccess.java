package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 欧源 on 2017/4/28.
 * 回复成功返回的数据
 */

public class ReplySuccess {
    /**
     * "id":201,
     * "member_nick":"\u554a",
     * "content":"\u6bd4\u8f83\u65a4\u65a4\u8ba1\u8f83\u597d",
     * "replay_to":"\u554a",
     * "comment_num":"0"
     */

    public int id;

    @SerializedName("member_nick")
    public String nickname;

    public String content;

    @SerializedName("replay_to")
    public String replayTo;

    @SerializedName("comment_num")
    public int commentNum;


}
