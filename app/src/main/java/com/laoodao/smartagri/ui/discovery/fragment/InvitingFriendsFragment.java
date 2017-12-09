package com.laoodao.smartagri.ui.discovery.fragment;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.discovery.contract.InvitingFriendsContract;
import com.laoodao.smartagri.ui.discovery.dialog.CottonDialog;
import com.laoodao.smartagri.ui.discovery.presenter.InvitingFriendsPresenter;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/7/21.
 */

public class InvitingFriendsFragment extends LazyFragment<InvitingFriendsPresenter> implements InvitingFriendsContract.InvitingFriendsView {
    @BindView(R.id.tv_total_company_num)
    TextView tvTotalCompanyNum;
    @BindView(R.id.tv_weight_total)
    TextView tvWeightTotal;
    @BindView(R.id.tv_share)
    RoundTextView mTvShare;
    @BindView(R.id.tv_frequency)
    TextView mTvFrequency;
    @BindView(R.id.tv_net_weight)
    TextView mTvNetWeight;
    @BindView(R.id.tv_enterprise)
    TextView mTvEnterprise;
    @BindView(R.id.tv_percentage)
    TextView mTvPercentage;
    private ShareDialog dialog;
    private CottonBill cottonBill;
    private CottonDialog mCottonDialog;

    @Override
    public void complete() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_inviting_friends_bill;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {

        cottonBill = SharedPreferencesUtil.getInstance().getObject(Constant.COTTON_BILL);
        if (cottonBill != null && cottonBill.now != null) {
            tvTotalCompanyNum.setText(UiUtils.getString(R.string.inviting_friends_mark1, cottonBill.now.total_company_num));
            tvWeightTotal.setText(UiUtils.getString(R.string.inviting_friends_mark2, cottonBill.now.weight_total));
            mTvFrequency.setText(Html.fromHtml(UiUtils.getString(R.string.transaction_num, cottonBill.now.sell_count)));
            mTvNetWeight.setText(Html.fromHtml(UiUtils.getString(R.string.net_weight, cottonBill.now.weight_total)));
            mTvPercentage.setText(Html.fromHtml(UiUtils.getString(R.string.lint_percentage, cottonBill.now.average_gin)));
            mTvEnterprise.setText(Html.fromHtml(UiUtils.getString(R.string.enterprise_num, cottonBill.now.total_company_num)));
        }

    }

    @OnClick(R.id.tv_share)
    public void onClick() {
        if (dialog == null) {
            dialog = new ShareDialog(getContext());
        }
        dialog.setShareInfo(cottonBill.shareInfo);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareChange(ShareEvent event) {
        mPresenter.cottonBack("cottonfield", 0);
    }

    @Override
    public void checkFirst(int share) {
        if (share != 1) {
            return;
        }
        if (mCottonDialog == null) {
            mCottonDialog = new CottonDialog(getContext());
        }
        mCottonDialog.setData("分享成功，获得免费刮奖机会一次！", "");
        mCottonDialog.show();
    }
}
