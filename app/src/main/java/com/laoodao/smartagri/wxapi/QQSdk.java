package com.laoodao.smartagri.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.utils.LogUtil;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;

public class QQSdk {

    Activity mContext;
    Tencent mTencent;
    QQListener mListener;


    public QQSdk(Activity context, String appId) {
        mContext = context;
        mTencent = Tencent.createInstance(appId, context);
    }

    public void authorize(final OnLoginOk<QQToken> listener) {
        if (mTencent.isSessionValid()) {
            listener.onLogin(mTencent.getQQToken());
        } else {
            mListener = new QQListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    initOpenidAndToken(values);
                    listener.onLogin(mTencent.getQQToken());
                }
            };
            mTencent.login(mContext, "get_simple_userinfo", mListener);
        }
    }

    public void shareImg(String imageUrl) {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        IUiListener listener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {
            }
        };
        mTencent.shareToQQ(mContext, params, listener);
    }


    public void shareToQQ(String title, String desc, String photo, String url, boolean isQQ) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("summary", desc);
        if (isQQ) {
            bundle.putString("imageUrl", photo);
        } else {
            ArrayList images = new ArrayList();
            images.add(photo);
            bundle.putStringArrayList("imageUrl", images);
        }
        bundle.putString("targetUrl", url);
        bundle.putString("appName", mContext.getResources().getString(R.string.app_name));

        if (isQQ) {
            mTencent.shareToQQ(mContext, bundle, baseUIListener);
        } else {
            mTencent.shareToQzone(mContext, bundle, baseUIListener);
        }
    }

    BaseUIListener baseUIListener = new BaseUIListener();

    private class BaseUIListener implements IUiListener {

        @Override
        public void onComplete(Object o) {

            EventBus.getDefault().post(new ShareEvent());
        }

        @Override
        public void onError(UiError uiError) {
        }

        @Override
        public void onCancel() {
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE ||
                    resultCode == Constants.REQUEST_QZONE_SHARE ||
                    resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, baseUIListener);
            }
        }
    }


    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface OnLoginOk<T> {
        void onLogin(T info);
    }

    public static class QQListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            LogUtil.e("登录成功: " + response.toString());
            doComplete((JSONObject) response);
        }

        protected void doComplete(final JSONObject values) {

        }


        @Override
        public void onError(UiError e) {
            LogUtil.e("错误信息:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            LogUtil.e("取消了当前的登录操作");
        }
    }


}