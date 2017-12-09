package com.laoodao.smartagri.ui.market.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.view.TextViewDrawable;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ShareDialog extends BottomBaseDialog<ShareDialog> {
    @BindView(R.id.btn_wx)
    TextViewDrawable mBtnWx;
    @BindView(R.id.btn_friend)
    TextViewDrawable mBtnFriend;
    @BindView(R.id.btn_qq)
    TextViewDrawable mBtnQq;
    @BindView(R.id.btn_qzone)
    TextViewDrawable mBtnQzone;
    @BindView(R.id.cancel)
    FrameLayout mCancel;

    ShareInfo mShareInfo;

    // 微信SDK
    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;

    public ShareDialog(Context context) {
        super(context);
    }

    public void setShareInfo(ShareInfo info) {
        mShareInfo = info;
    }


    @Override
    public View onCreateView() {
        showAnim(null);
        dismissAnim(null);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_share, mLlControlHeight, false);
        KnifeUtil.bindTarget(this, inflate);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    @OnClick({R.id.btn_wx, R.id.btn_friend, R.id.btn_qq, R.id.btn_qzone, R.id.cancel})
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.btn_wx:
            case R.id.btn_friend:
            case R.id.btn_qq:
            case R.id.btn_qzone:
                if (mShareInfo != null)
                    share(view.getId());
                break;
            case R.id.tv_cancel:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mQQSdk.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 分享
     *
     * @param resId
     */
    private void share(int resId) {
        switch (resId) {
            case R.id.btn_wx:
            case R.id.btn_friend:
                int scene = resId == R.id.btn_friend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                if (mWechatSdk == null) {
                    mWechatSdk = new WechatSdk(mContext, BuildConfig.APP_ID_WECHAT);
                }
                mWechatSdk.share(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, scene);
                break;
            case R.id.btn_qq:
            case R.id.btn_qzone:
                if (mQQSdk == null) {
                    mQQSdk = new QQSdk((Activity) mContext, BuildConfig.APP_ID_QQ);
                }
                mQQSdk.shareToQQ(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, resId == R.id.btn_qq);
                break;
        }
    }

}
