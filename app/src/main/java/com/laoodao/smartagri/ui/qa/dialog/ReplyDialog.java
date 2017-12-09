package com.laoodao.smartagri.ui.qa.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/28.
 */

public class ReplyDialog extends BaseDialog<ReplyDialog> {

    @BindView(R.id.et_reply)
    EditText mEtReply;

    public CallBack mCallBack;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;

    public ReplyDialog(Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_reply, mLlControlHeight, false);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {
//        mEtReply.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    String reply = mEtReply.getText().toString();
//                    DeviceUtils.hideSoftKeyboard(mContext, mEtReply);
//                    mEtReply.setText(null);
//                    dismiss();
//                    if (mCallBack != null)
//                        mCallBack.onResult(reply);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    @OnClick({R.id.tv_cancel, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_commit:
                String reply = mEtReply.getText().toString().trim();
                if (TextUtils.isEmpty(reply)) {
                    UiUtils.makeText("请输入回复内容");
                    return;
                }
                dismiss();
                DeviceUtils.hideSoftKeyboard(mContext, mEtReply);
                mEtReply.setText(null);
                dismiss();
                if (mCallBack != null)
                    mCallBack.onResult(reply);
                break;
        }
    }


    public interface CallBack {
        void onResult(String reply);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mLlTop.setGravity(Gravity.BOTTOM);
        getWindow().setGravity(Gravity.BOTTOM);
        DeviceUtils.showSoftKeyboard(this);
    }

    @Override
    public void dismiss() {
        DeviceUtils.hideSoftKeyboard(mContext, mEtReply);
        super.dismiss();
    }
}
