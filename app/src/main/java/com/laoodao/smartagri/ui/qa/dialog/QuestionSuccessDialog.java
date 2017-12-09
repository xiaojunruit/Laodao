package com.laoodao.smartagri.ui.qa.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.AskSuccess;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class QuestionSuccessDialog extends BaseDialog {
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.iv_wx)
    ImageView mIvWx;
    @BindView(R.id.iv_friend)
    ImageView mIvFriend;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.iv_qzone)
    ImageView mIvQzone;
    @BindView(R.id.ll_jifen)
    LinearLayout mLlJifen;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;

    ShareInfo mShareInfo;
    String mPoints;
    boolean isPointsAdd;
    public boolean dialogIsShow;

    public QuestionSuccessDialog(Context context) {
        super(context);
    }

    public void setData(AskSuccess askSuccess) {
        mShareInfo = askSuccess.shareInfo;
        mPoints = askSuccess.points;
        isPointsAdd = askSuccess.isPointsAdd;
    }

    @Override
    public View onCreateView() {
        showAnim(null);
        dismissAnim(null);
        widthScale(0.85f);
        View view = getLayoutInflater().inflate(R.layout.dialog_question_success, mLlControlHeight, false);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        if (isPointsAdd) {
            mTvIntegral.setText(mPoints + "积分");
            mLlJifen.setVisibility(View.VISIBLE);
            mFlContent.setBackgroundResource(R.mipmap.ic_integral_bg);
        } else {
            mLlJifen.setVisibility(View.GONE);
            mFlContent.setBackgroundResource(R.mipmap.ic_no_points_add);
        }
    }

    @OnClick({R.id.iv_close, R.id.iv_wx, R.id.iv_friend, R.id.iv_qq, R.id.iv_qzone})
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.iv_close:
                dialogIsShow = false;
                break;
            case R.id.iv_wx:
            case R.id.iv_friend:
            case R.id.iv_qq:
            case R.id.iv_qzone:
                if (mShareInfo != null)
                    share(view.getId());
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mQQSdk.onActivityResult(requestCode, resultCode, data);
    }

    private void share(int resId) {
        switch (resId) {
            case R.id.iv_wx:
            case R.id.iv_friend:
                int scene = resId == R.id.iv_friend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                if (mWechatSdk == null) {
                    mWechatSdk = new WechatSdk(mContext, BuildConfig.APP_ID_WECHAT);
                }
                mWechatSdk.share(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, scene);
                break;
            case R.id.iv_qq:
            case R.id.iv_qzone:
                if (mQQSdk == null) {
                    mQQSdk = new QQSdk((Activity) mContext, BuildConfig.APP_ID_QQ);
                }
                mQQSdk.shareToQQ(mShareInfo.title, mShareInfo.content, mShareInfo.img, mShareInfo.url, resId == R.id.iv_qq);
                break;
        }
    }
}
