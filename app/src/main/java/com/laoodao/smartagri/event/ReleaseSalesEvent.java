package com.laoodao.smartagri.event;

/**
 * Created by Administrator on 2017/5/4.
 */

/**
 * 1编辑
 * 2添加
 */
public class ReleaseSalesEvent {
    public int type;

    public ReleaseSalesEvent(int type) {
        this.type = type;
    }
}
