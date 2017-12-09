package com.laoodao.smartagri.ui.market.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.flyco.dialog.widget.base.BaseDialog;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.ui.market.activity.ReleaseBuyingActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseSupplyingActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.IconButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/18.
 * <p>
 * 发布供求
 */

public class ReleaseSupplyDemandDialog extends BaseDialog<ReleaseSupplyDemandDialog> {

    @BindView(R.id.btn_buy)
    IconButton mBtnBuy;
    @BindView(R.id.btn_sales)
    IconButton mBtnSales;


    public ReleaseSupplyDemandDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_release_supply_demand, mLlControlHeight, false);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {

    }

    @OnClick({R.id.btn_buy, R.id.btn_sales})
    public void onClick(View view) {
        dismiss();
        if (!Global.getInstance().isLoggedIn()) {
            UiUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.btn_buy:
                ReleaseBuyingActivity.start(getContext(), "发布求购", -1);
                break;
            case R.id.btn_sales:
                ReleaseSupplyingActivity.start(mContext, "发布供销", -1);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        int y = DeviceUtils.getStatuBarHeight(mContext);
        params.dimAmount = 0;
        params.gravity = Gravity.TOP;
        params.y = y;
        mLlTop.setBackgroundColor(0xb3000000);
        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getScreenHeight(mContext) - y)));
    }
}
