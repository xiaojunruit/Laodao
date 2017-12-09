package com.laoodao.smartagri.ui.user.activity;

import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.MyLoan;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.MyLoanContract;
import com.laoodao.smartagri.ui.user.presenter.MyLoanPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class MyLoanActivity extends BaseActivity<MyLoanPresenter> implements MyLoanContract.MyLoanView {
    @BindView(R.id.btn_detail)
    RoundTextView mBtnDetail;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    @BindView(R.id.tv_surplus)
    TextView mTvSurplus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_loan;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mPresenter.requestMyLoan();
    }

    @Override
    public void complete() {

    }

    @OnClick(R.id.btn_detail)
    public void onClick() {
        UiUtils.startActivity(this, LoanRecordActivity.class);
    }

    @Override
    public void myLoadSuccess(MyLoan myLoan) {
        mTvSurplus.setText(UiUtils.getString(R.string.total_money, myLoan.totalMoney));
        mTvTotalMoney.setText(myLoan.surplusMoney);
    }
}
