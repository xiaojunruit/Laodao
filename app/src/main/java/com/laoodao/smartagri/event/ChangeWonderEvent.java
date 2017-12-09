package com.laoodao.smartagri.event;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ChangeWonderEvent {

    public int mMsg;
    public int mPosition;

    public ChangeWonderEvent(int msg,int position) {
        mMsg = msg;
        mPosition=position;
    }
}
