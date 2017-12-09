package com.laoodao.smartagri.ui.user.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.flyco.dialog.widget.base.BaseDialog;
import com.laoodao.smartagri.R;

/**
 * Created by WORK on 2017/8/15.
 */

public class UserHomePageMenuDialog extends BaseDialog<UserHomePageMenuDialog> {
    private View mAnchorView;
    public UserHomePageMenuDialog(Context context,View anchorView) {
        super(context);
        this.mAnchorView = anchorView;
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_user_home_page_menu, null, false);
        return view;
    }

    @Override
    public void setUiBeforShow() {

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
        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mContext.getResources().getDisplayMetrics().heightPixels + y));

    }
}
