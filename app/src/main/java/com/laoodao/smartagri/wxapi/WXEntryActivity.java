package com.laoodao.smartagri.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.event.Login3rdEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new Login3rdEvent(1999, -1, getIntent()));

        mWxApi = WXAPIFactory.createWXAPI(this, BuildConfig.APP_ID_WECHAT, false);
        mWxApi.registerApp(BuildConfig.APP_ID_WECHAT);
        mWxApi.handleIntent(getIntent(), this);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        EventBus.getDefault().post(new Login3rdEvent(1999, -1, intent));
        mWxApi.handleIntent(intent, this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //  Log.e("-=-=-=-=", "错误号：" + baseResp.errCode + "；信息：" + baseResp.errStr);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                EventBus.getDefault().post(new ShareEvent());
                // Log.e("=-=-=-=-=", "成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //  Log.e("=-=-=-=-=", "取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //  Log.e("=-=-=-=-=", "被拒绝");
                break;
            default:
                // Log.e("=-=-=-=-=", "返回");
                break;
        }

    }
}