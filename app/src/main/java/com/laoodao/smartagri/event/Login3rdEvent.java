package com.laoodao.smartagri.event;

import android.content.Intent;

/**
 * Created by WORK on 2017/4/28.
 */

public class Login3rdEvent {
    public int requestCode;
    public int resultCode;
    public Intent data;

    public Login3rdEvent(int requestCode, int resultCode, Intent data){
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}
