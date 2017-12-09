package com.laoodao.smartagri.ui.qa.dialog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.popup.base.BasePopup;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.event.ChangeWonderEvent;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/4/17.
 */

public class MorePopup extends BasePopup<MorePopup> {

    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_center)
    TextView mTvCenter;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    private int mid;
    private int mIsFollow; //1关注 0未关注;
    private int mPosition;
    private View.OnClickListener mOnClickListener;

    public MorePopup(Context context, int id, int isFollow, int position) {
        super(context);
        mid = id;
        mIsFollow = isFollow;
        mPosition = position;
    }

    @Override
    public View onCreatePopupView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_more_popup, null, false);
        KnifeUtil.bindTarget(this, v);
        return v;
    }


    public void setOnMenuItemClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


    @Override
    public void setUiBeforShow() {
        if (mIsFollow == 1) {
            mTvLeft.setText("取消关注");
        } else {
            mTvLeft.setText("关注此问题");
        }
    }

    @OnClick({R.id.tv_left, R.id.tv_center, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
            case R.id.tv_center:
            case R.id.tv_right:
                dismiss();
                mOnClickListener.onClick(view);
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().post(
                new ChangeWonderEvent(mIsFollow, mPosition));
    }
}
