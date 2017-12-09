package com.laoodao.smartagri.event;

import com.laoodao.smartagri.bean.NewMessage;

/**
 * Created by WORK on 2017/7/12.
 */

public class RefreshMsgEvent {
    public NewMessage newMessage;

    public RefreshMsgEvent(NewMessage newMessage) {
        this.newMessage = newMessage;
    }
}
