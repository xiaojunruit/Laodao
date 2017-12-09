package com.laoodao.smartagri.ui.user.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.flyco.dialog.widget.base.TopBaseDialog;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.IDCardUtil;
import com.laoodao.smartagri.utils.KnifeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/2.
 */

public class FarmingCapitalSelectDialog extends TopBaseDialog<FarmingCapitalSelectDialog> {

    @BindView(R.id.tv_my_order)
    RoundTextView mTvMyOrder;
    @BindView(R.id.tv_repayment_record)
    RoundTextView mTvRepaymentRecord;
    @BindView(R.id.tv_return)
    RoundTextView mTvReturn;
    private View mAnchorView;
    private Context mContext;
    private int mTab;
    private OnTagClick mOnTagClick;

    public void setOnTagClick(OnTagClick onTagClick) {
        this.mOnTagClick = onTagClick;
    }

    public FarmingCapitalSelectDialog(Context context, View anchorView, int tab) {
        super(context);
        this.mContext = context;
        this.mAnchorView = anchorView;
        this.mTab = tab;
    }


    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_farming_capital_select, null, false);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    public void setMtab(int tab) {
        this.mTab = tab;
    }

    @Override
    public void setUiBeforShow() {
        if (mTab == 0) {
            tabState(mTvMyOrder, mTvRepaymentRecord, mTvReturn);
        } else if (mTab == 1) {
            tabState(mTvRepaymentRecord, mTvMyOrder, mTvReturn);
        } else {
            tabState(mTvReturn, mTvRepaymentRecord, mTvMyOrder);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        int location[] = new int[2];
        final Rect rect = new Rect();
        //获取显示范围，不包括状态栏
        mAnchorView.getWindowVisibleDisplayFrame(rect);
        //获取anchorView 在屏幕的坐标
        mAnchorView.getLocationOnScreen(location);
        int y = location[1] + mAnchorView.getHeight();
        params.dimAmount = 0;
        params.gravity = Gravity.TOP;
        params.x = location[0];
        params.y = y;
        mLlTop.setBackgroundColor(0x80000000);
        mLlTop.setGravity(Gravity.TOP);
        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContext.getResources().getDisplayMetrics().heightPixels - y));
    }

    @OnClick({R.id.tv_my_order, R.id.tv_repayment_record, R.id.tv_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_return:
                if (mTvReturn.isSelected()) {
                    return;
                }
                tabState(mTvReturn, mTvMyOrder, mTvRepaymentRecord);
                mOnTagClick.setOnClick(view);
                break;
            case R.id.tv_my_order:
                if (mTvMyOrder.isSelected()) {
                    return;
                }
                tabState(mTvMyOrder, mTvRepaymentRecord, mTvReturn);
                mOnTagClick.setOnClick(view);
                break;
            case R.id.tv_repayment_record:
                if (mTvRepaymentRecord.isSelected()) {
                    return;
                }
                tabState(mTvRepaymentRecord, mTvMyOrder, mTvReturn);
                mOnTagClick.setOnClick(view);
                break;
        }
    }

    private void tabState(RoundTextView viewSu, RoundTextView viewUn, RoundTextView viewUn2) {
        viewSu.getDelegate().setBackgroundColor(0xff0081cc);
        viewSu.setSelected(true);
        viewUn.getDelegate().setBackgroundColor(0xffcccccc);
        viewUn.setSelected(false);
        viewUn2.getDelegate().setBackgroundColor(0xffcccccc);
        viewUn2.setSelected(false);
    }

    public interface OnTagClick {
        void setOnClick(View view);
    }

}
