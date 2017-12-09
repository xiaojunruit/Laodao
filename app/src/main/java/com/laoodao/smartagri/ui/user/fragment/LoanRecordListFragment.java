package com.laoodao.smartagri.ui.user.fragment;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.bean.LoanRecord;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.LoanRecordListContract;
import com.laoodao.smartagri.ui.user.presenter.LoanRecordListPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/14.
 */

public class LoanRecordListFragment extends BaseFragment<LoanRecordListPresenter> implements LoanRecordListContract.LoanRecordListView {


    @BindView(R.id.tv_loan_total_money)
    TextView mTvLoanTotalMoney;
    @BindView(R.id.tv_loan_time)
    TextView mTvLoanTime;
    @BindView(R.id.img_state)
    ImageView mImgState;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_loan_num)
    TextView mTvLoanNum;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id_card)
    TextView mTvIdCard;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_loan_money)
    TextView mTvLoanMoney;
    @BindView(R.id.tv_purpose)
    TextView mTvPurpose;
    @BindView(R.id.tv_appointed_time)
    TextView mTvAppointedTime;
    @BindView(R.id.tv_loan_interest)
    TextView mTvLoanInterest;
    @BindView(R.id.tv_bank_nmae)
    TextView mTvBankNmae;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_statistics)
    TextView mTvStatistics;
    @BindView(R.id.tv_state_desc)
    TextView mTvStateDesc;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private LoanRecord loanRecord;

    @Override
    public void complete() {

    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_loan_record_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        onRefresh();
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });
            mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });

        }
    }

    private void onRefresh() {
        mPresenter.requestLoadRecord();
    }

    @Override
    public void loadRecordSuccess(LoanRecord loanRecord) {
        this.loanRecord = loanRecord;
        bindData(mTvName, R.string.loan_name, loanRecord.name);
        bindData(mTvIdCard, R.string.loan_id_card, loanRecord.idCard);
        bindData(mTvPhone, R.string.phone, loanRecord.phone);
        bindData(mTvLoanMoney, R.string.loan_money, loanRecord.totalMoney);
        bindData(mTvLoanNum, R.string.loan_num, loanRecord.contractNo);
        bindData(mTvPurpose, R.string.loan_purpose, loanRecord.purpose);
        bindData(mTvAppointedTime, R.string.loan_appointed_time, loanRecord.expireTime);
        bindData(mTvAccount, R.string.loan_account, loanRecord.account);
        bindData(mTvBankNmae, R.string.loan_bank_name, loanRecord.bank);
        bindData(mTvLoanInterest, R.string.loan_interest, loanRecord.rate);
        bindData(mTvStatistics, R.string.loan_statistics, loanRecord.statNo);
        bindData(mTvStateDesc, R.string.loan_state_desc, loanRecord.stateName);
        bindData(mTvLoanTotalMoney, R.string.loan_total_money, loanRecord.totalMoney);
        bindData(mTvState, R.string.loan_state, loanRecord.stateName);
        bindData(mTvLoanTime, R.string.loan_time, loanRecord.startTime);
        switch (loanRecord.state) {
            case 1:
                mImgState.setImageResource(R.mipmap.ic_state_blue_su);
                break;
        }
    }

    private void bindData(TextView view, int stringValue, String value) {
        view.setText(UiUtils.getString(stringValue, value));
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (loanRecord != null) {
                if (TextUtils.isEmpty(loanRecord.contractNo)) {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
            }
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

    }

    ;


}
