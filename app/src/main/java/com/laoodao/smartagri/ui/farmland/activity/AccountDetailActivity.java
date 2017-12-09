package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.base.AccountDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.BillChangeEvent;
import com.laoodao.smartagri.event.RemoveBillChangeEvent;
import com.laoodao.smartagri.ui.farmland.contract.AccountDetailContract;
import com.laoodao.smartagri.ui.farmland.presenter.AccountDetailPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AccountDetailActivity extends BaseActivity<AccountDetailPresenter> implements AccountDetailContract.AccountDetailView {
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.riv_img)
    RoundedImageView mRivImg;
    @BindView(R.id.rtv_edit)
    RoundTextView mRtvEdit;
    @BindView(R.id.rtv_delete)
    RoundTextView mRtvDelete;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;
    public AccountDetail detail;
    private int id;
    private int position;


    public static void start(Context context, int id, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("position", position);
        UiUtils.startActivity(context, AccountDetailActivity.class, bundle);
    }


    @Override
    public void complete() {

    }


    @Override
    public void initAccountDetail(AccountDetail detail) {
        this.detail = detail;
        String type;
        mTvMoney.setText(detail.account);
        if (Constant.EXPENDITURE == detail.accountType) {
            type = "支出";
        } else {
            type = "收入";
        }
        mTvType.setText(type);
        mTvDate.setText(detail.date);
        mTvRemark.setText(detail.remark);
        Glide.with(getApplicationContext())
                .load(detail.imgarr.get(0))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .centerCrop()
                .into(mRivImg);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void billChange(BillChangeEvent event) {
        if (id != 0) {
            mPresenter.requestAccountDetail(id);
        }
    }

    @Override
    public void getResult(Response response) {
        EventBus.getDefault().post(new RemoveBillChangeEvent(position));
        UiUtils.makeText(response.message);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill_details;

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);
        mPresenter.requestAccountDetail(id);
    }

    @OnClick({R.id.rtv_edit, R.id.rtv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_edit:
                IncomeRecordEditActivity.start(this, detail, id);
                break;
            case R.id.rtv_delete:
                NormalDialog dialog = new NormalDialog(this);
                dialog.content("确定要删除账单吗？辛苦记的帐就找不回来啦！")
                        .contentGravity(Gravity.CENTER)
                        .contentTextColor(R.color.common_h1)
                        .isTitleShow(false)
                        .cornerRadius(5)
                        .contentTextSize(16f)
                        .btnTextSize(14f, 14f)
                        .btnTextColor(R.color.common_h3, R.color.common_h3)
                        .show();
                dialog.setOnBtnClickL(() -> {
                    dialog.dismiss();
                }, () -> {
                    mPresenter.requestResult(id);
                });
                break;
        }
    }
}
