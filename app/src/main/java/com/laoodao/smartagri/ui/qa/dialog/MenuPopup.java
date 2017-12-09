package com.laoodao.smartagri.ui.qa.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.popup.base.BasePopup;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.KnifeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/28.
 */

public class MenuPopup extends BasePopup<MenuPopup> {


    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_center)
    TextView mTvCenter;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.line1)
    View mLine1;
    @BindView(R.id.line2)
    View mLine2;

    private View.OnClickListener mOnClickListener;

    public MenuPopup(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.showAnim(null)
                .dismissAnim(null)
                .offset(-40, 25)
                .dimEnabled(false)
                .gravity(Gravity.TOP);

        this.mOnClickListener = onClickListener;
    }

    @Override
    public View onCreatePopupView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_more_popup, null, false);
        KnifeUtil.bindTarget(this, v);
        return v;
    }

    @Override
    public void setUiBeforShow() {

    }

    @OnClick({R.id.tv_left, R.id.tv_center, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
            case R.id.tv_center:
            case R.id.tv_right:
                dismiss();
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(view);
                }
                break;
        }
    }

    public void setCenterVisible(boolean visible) {
        mTvCenter.setVisibility(visible ? View.VISIBLE : View.GONE);
        mLine1.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setLeftText(String leftText) {
        mTvLeft.setText(leftText);
    }

    public void setRightText(String rightText) {
        mTvRight.setText(rightText);
    }

    public void setCenterText(String centerText) {
        mTvCenter.setText(centerText);
    }


    public interface OnMenuItemClickListener {
        void onLeftClick();
    }


}
