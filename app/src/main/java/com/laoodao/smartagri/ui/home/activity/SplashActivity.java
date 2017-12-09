package com.laoodao.smartagri.ui.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.roundview.RoundTextView;
import com.hyphenate.chat.EMClient;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.RxUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Administrator on 2017/5/6.
 */

public class SplashActivity extends RxAppCompatActivity {

    RoundTextView mBtnSkip;
    TextView mBtnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBtnSkip = (RoundTextView) findViewById(R.id.btn_skip);
        mBtnStart = (TextView) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(v -> {
            start();
        });
        mBtnSkip.setOnClickListener(v -> {
            start();
        });
        int lastVersionCode = Global.getInstance().getLastVersionCode();
        if (BuildConfig.VERSION_CODE > lastVersionCode) {
            UiUtils.startActivity(GuideActivity.class);
            finish();
        } else {
            requestPermission();
        }
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String pid = uri.getQueryParameter("pid");
        }
        if (EMClient.getInstance().isLoggedInBefore()) {
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
        }
    }

    private void requestPermission() {
        RxUtils.countdown(3).compose(bindToLifecycle()).doOnCompleted(() -> start()).
                subscribe(seconds -> {
                    mBtnSkip.setText(getString(R.string.skip, String.valueOf(seconds)));
                });
    }

//    private void start() {
//        int lastVersionCode = Global.getInstance().getLastVersionCode();
//        if (BuildConfig.VERSION_CODE > lastVersionCode) {
//            UiUtils.startActivity(GuideActivity.class);
//        } else {
//            MainActivity.start(this, 0);
//        }
//        finish();
//    }

    private void start() {
        UiUtils.startActivity(MainActivity.class);
        finish();
    }
}
