package com.laoodao.smartagri.ui.discovery.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.KnifeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class CottonDialog extends BaseDialog {
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.rtv_determine)
    RoundTextView rtvDetermine;
    private Context mContext;
    private String mPrompt;
    private String mOperation;

    public CottonDialog(Context context) {
        super(context);
        widthScale(0.8f);
        mContext = context;
    }

    public CottonDialog(Context context, String prompt, String operation) {
        super(context);
        mContext = context;
        mPrompt = prompt;
        mOperation = operation;
        widthScale(0.8f);
    }


    public void setData(String prompt, String operation) {
        mPrompt = prompt;
        mOperation = operation;
    }

    @Override
    public View onCreateView() {
        View view = LinearLayout.inflate(mContext, R.layout.cotton_dialog, null);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        tvPrompt.setText(mPrompt);
        tvOperation.setText(mOperation);
        if (TextUtils.isEmpty(mOperation)) {
            tvOperation.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.rtv_determine)
    public void onClick() {
        this.dismiss();
    }
}
