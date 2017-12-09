package com.laoodao.smartagri.ui.discovery.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class SharePopup extends BasePopup<SharePopup> {
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_share)
    TextView tvShare;
    private View.OnClickListener mOnClickListener;
    private int mIsWonder;


    public SharePopup(Context context, View.OnClickListener onClickListener, int isWonder) {
        super(context);
        mOnClickListener = onClickListener;
        mIsWonder = isWonder;
    }

    @Override
    public View onCreatePopupView() {
        this.showAnim(null)
                .dismissAnim(null)
                .dimEnabled(false)
                .gravity(Gravity.TOP);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_more_popup, null, false);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_share_blue);
        drawable.setBounds(0, 0, UiUtils.dip2px(17.3f), UiUtils.dip2px(17.3f));
        tvShare.setCompoundDrawables(drawable, null, null, null);
        if (mIsWonder == 1) {
            tvCollect.setSelected(true);
        }

    }

    @OnClick({R.id.tv_collect, R.id.tv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                tvCollect.setSelected(!tvCollect.isSelected());
            case R.id.tv_share:
                dismiss();
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(view);
                }
                break;
        }
    }
}
