package com.laoodao.smartagri.ui.discovery.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.laoodao.smartagri.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/8/9.
 */

public class CallMapDialog extends BottomBaseDialog<CallMapDialog> {


    @BindView(R.id.tv_call_gaode)
    TextView mTvCallGaode;
    @BindView(R.id.tv_call_baidu)
    TextView mTvCallBaidu;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_call_share)
    TextView mTvCallShare;
    public OnCallBaiDuListener mOnCallBaiDuListener;
    public OnCallGaoDeListener mOnCallGaoDeListener;
    public OnCallShareListener mOnCallShareListener;


    public void setOnCallBaiDuListener(OnCallBaiDuListener onCallBaiDuListener) {
        this.mOnCallBaiDuListener = onCallBaiDuListener;
    }

    public void setOnCallGaoDeListener(OnCallGaoDeListener onCallGaoDeListener) {
        this.mOnCallGaoDeListener = onCallGaoDeListener;
    }

    public void setOnCallShareListener(OnCallShareListener onCallShareListener) {
        this.mOnCallShareListener = onCallShareListener;
    }

    public CallMapDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_call_map, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {

    }

    @OnClick({R.id.tv_call_gaode, R.id.tv_call_baidu, R.id.tv_cancel,R.id.tv_call_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call_gaode:
                mOnCallGaoDeListener.setOnCallGaoDeListener();
                dismiss();
                break;
            case R.id.tv_call_baidu:
                mOnCallBaiDuListener.setOnCallBaiDuListener();
                dismiss();
                break;
            case R.id.tv_call_share:
                mOnCallShareListener.setOnCallShareListener();
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface OnCallBaiDuListener {
        void setOnCallBaiDuListener();
    }

    public interface OnCallGaoDeListener {
        void setOnCallGaoDeListener();
    }

    public interface OnCallShareListener {
        void setOnCallShareListener();
    }
}
