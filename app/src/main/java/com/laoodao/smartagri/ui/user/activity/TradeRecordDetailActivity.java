package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.TradeRecordChangeEvent;
import com.laoodao.smartagri.ui.user.adapter.TransactionRecordChildAdapter;
import com.laoodao.smartagri.ui.user.contract.TradeRecordDetailContract;
import com.laoodao.smartagri.ui.user.presenter.TradeRecordDetailPresenter;
import com.laoodao.smartagri.utils.CodeCountDown;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/19.
 */

public class TradeRecordDetailActivity extends BaseActivity<TradeRecordDetailPresenter> implements TradeRecordDetailContract.TradeRecordDetailView {
    @BindView(R.id.transaction_detail)
    LinearLayout mTransactionDetail;
    @BindView(R.id.detail_tab)
    FrameLayout mDetailTab;
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_submit)
    RoundTextView mTvSubmit;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_order_money)
    TextView mTvOrderMoney;
    @BindView(R.id.tv_trade_time)
    TextView mTvTradeTime;
    @BindView(R.id.tv_available_credit)
    TextView mTvAvailableCredit;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.tv_store_name)
    TextView mTvStoreName;
    @BindView(R.id.tv_store_controller)
    TextView mTvStoreController;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_trade_money)
    TextView mTvTradeMoney;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    private TransactionRecordChildAdapter mAdapter;
    private boolean isDetailShow;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, TradeRecordDetailActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_record_detail;
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
        id = getIntent().getIntExtra("id", 0);
        mAdapter = new TransactionRecordChildAdapter(this);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter);
        mPresenter.requestPayment(id);
    }

    @OnClick({R.id.detail_tab, R.id.tv_submit, R.id.tv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_tab:
                isDetailShow = !isDetailShow;
                weatherAnimation(isDetailShow ? 0 : 90, isDetailShow ? 90 : 0);
                mTransactionDetail.setVisibility(isDetailShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_code:
                if (id != 0) {
                    mPresenter.tradeCode(id);
                }
                break;
            case R.id.tv_submit:
                String code = mEtCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    UiUtils.makeText("请输入验证码");
                    return;
                }
                mPresenter.tradePay(id, code);
                break;
        }
    }


    private void weatherAnimation(float from, float to) {
        RotateAnimation animation = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(0);
        animation.setFillAfter(true);
        mIvArrow.startAnimation(animation);
    }

    @Override
    public void tradeRecodeDetailSucess(TradeRecord tradeRecord) {
        mTvTradeTime.setText(tradeRecord.addTime);
        mTvOrderNum.setText(tradeRecord.orderSn);
        bindData(mTvAvailableCredit, R.string.money_symbol, tradeRecord.surplusMoney);
        bindData(mTvOrderMoney, R.string.money_symbol, tradeRecord.orderMoney);
        bindData(mTvPhone, R.string.contact_phone, tradeRecord.storePhone);
        bindData(mTvStoreName, R.string.store_name, tradeRecord.storeName);
        bindData(mTvStoreController, R.string.store_controller, tradeRecord.storeChief);
        bindData(mTvTradeMoney, R.string.trade_money, tradeRecord.orderMoney);
        bindData(mTvTotalMoney, R.string.total, tradeRecord.orderMoney);
        mAdapter.addAll(tradeRecord.detail);
    }

    private void bindData(TextView view, int stringValue, String value) {
        view.setText(UiUtils.getString(stringValue, value));
    }

    @Override
    public void tradeCodeSuccess() {
        new CodeCountDown(mTvCode).start();
    }

    @Override
    public void tradePaySuccess() {
        EventBus.getDefault().post(new TradeRecordChangeEvent());
        finish();
    }


}
