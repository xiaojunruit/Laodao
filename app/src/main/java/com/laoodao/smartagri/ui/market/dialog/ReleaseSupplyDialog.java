package com.laoodao.smartagri.ui.market.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.ui.market.activity.ReleaseBuyingActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseSupplyingActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.IconButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/19.
 */

public class ReleaseSupplyDialog extends BottomBaseDialog<ReleaseSupplyDialog> {

    @BindView(R.id.btn_buy)
    IconButton mBtnBuy;
    @BindView(R.id.btn_sales)
    IconButton mBtnSales;

    public ReleaseSupplyDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_release_supply, null, false);
        KnifeUtil.bindTarget(this, view);
        return view;
    }

    @OnClick({R.id.btn_buy, R.id.btn_sales, R.id.close})
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.btn_buy:
                if (!Global.getInstance().isLoggedIn()) {
                    UiUtils.startActivity(LoginActivity.class);
                    return;
                }
                ReleaseBuyingActivity.start(getContext(), "发布求购", -1);
                break;
            case R.id.btn_sales:
                if (!Global.getInstance().isLoggedIn()) {
                    UiUtils.startActivity(LoginActivity.class);
                    return;
                }
                ReleaseSupplyingActivity.start(mContext, "发布供销", -1);
                break;
            case R.id.close:
                break;
        }
    }

    @Override
    public void setUiBeforShow() {

    }
}
