package com.laoodao.smartagri.event;

/**
 * Created by Administrator on 2017/5/5.
 */

public class CloseMessageEvent {
    public boolean notice;
    public boolean dynamic;
    public boolean answer;

    public CloseMessageEvent(boolean notice, boolean dynamic, boolean answer) {
        this.notice = notice;
        this.dynamic = dynamic;
        this.answer = answer;
    }
}
