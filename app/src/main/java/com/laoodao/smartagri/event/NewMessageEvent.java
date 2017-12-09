package com.laoodao.smartagri.event;

import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * Created by Administrator on 2017/5/5.
 */

public class NewMessageEvent {
    public boolean notice;
    public boolean dynamic;
    public boolean answer;

    public NewMessageEvent(boolean notice, boolean dynamic, boolean answer) {
        this.notice = notice;
        this.dynamic = dynamic;
        this.answer = answer;
    }
}
