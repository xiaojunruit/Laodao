package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/26.
 */

public class AnswerReply {
    public String id;
    @SerializedName("answer_id")
    public String answerId;
    public String title;
    public String content;
    public String member_name;
    public String member_avatar;

}
