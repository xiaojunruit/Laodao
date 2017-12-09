package com.laoodao.smartagri.ui.market.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.popup.base.BasePopup;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.KnifeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SalesMorePopup extends BasePopup<SalesMorePopup> {
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_center)
    TextView mTvCenter;
    @BindView(R.id.tv_right)
    TextView mTvRight;

    private View.OnClickListener mOnClickListener;


    public SalesMorePopup(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.showAnim(null)
                .dismissAnim(null)
                .dimEnabled(true)
                .offset(-45, 39)
                .dimEnabled(false)
                .gravity(Gravity.TOP);
        this.mOnClickListener = onClickListener;
    }

    @Override
    public View onCreatePopupView() {
        View v = getLayoutInflater().inflate(R.layout.item_more_popup, mLlControlHeight, false);
        KnifeUtil.bindTarget(this, v);
        return v;
    }

    @Override
    public void setUiBeforShow() {
        mTvLeft.setText("编辑");
        mTvCenter.setText("删除");
        mTvRight.setText("分享");

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
}
