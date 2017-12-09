package com.laoodao.smartagri.common;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.laoodao.smartagri.utils.LogUtil;


public class RouterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {
            Route.go(this, uri.toString());
        }
        finish();
    }
}