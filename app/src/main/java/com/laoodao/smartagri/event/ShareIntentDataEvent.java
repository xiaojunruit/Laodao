package com.laoodao.smartagri.event;

import android.content.Intent;

/**
 * Created by Administrator on 2017/6/9.
 */

public class ShareIntentDataEvent {
    public int requestCode;
    public int resultCode;
    public Intent data;

    public ShareIntentDataEvent(int requestCode, int resultCode, Intent data){
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}
