package com.laoodao.smartagri.ui.user.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.flyco.roundview.RoundFrameLayout;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.KnifeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2016/9/2.
 */

public class AvatarSelectDialog extends BottomBaseDialog<AvatarSelectDialog> {


    @BindView(R.id.btn_camera)
    RoundFrameLayout mBtnCamera;
    @BindView(R.id.btn_photo)
    RoundFrameLayout mBtnPhoto;
    @BindView(R.id.btn_cancel)
    RoundTextView mBtnCancel;
    @BindView(R.id.tv_text1)
    TextView mTvText1;
    @BindView(R.id.tv_text2)
    TextView mTvText2;
    //    private View.OnClickListener mListener;
    private Context mContext;
    private AvatarListener mListener;
    private boolean mIsIntroduction;

    public AvatarSelectDialog(Context context) {
        super(context);
        mContext = context;
    }

    public AvatarSelectDialog(Context context, boolean isIntroduction) {
        super(context);
        mContext = context;
        mIsIntroduction = isIntroduction;
    }

    public void setOnAvatarListener(AvatarListener listener) {
        this.mListener = listener;
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_avatar_select, null, false);
        KnifeUtil.bindTarget(this, view);
        widthScale(0.95f);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        if (mIsIntroduction) {
            mTvText1.setText("保存修改");
            mTvText2.setText("不保存");
        }
    }

    @OnClick({R.id.btn_camera, R.id.btn_photo, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
            case R.id.btn_photo:
                mListener.OnClickListener(view);
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public interface AvatarListener {
        void OnClickListener(View view);
    }

}
