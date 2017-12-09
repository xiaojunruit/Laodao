package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.ReleaseFormulaEvent;
import com.laoodao.smartagri.ui.discovery.contract.ReleaseFormulaContract;
import com.laoodao.smartagri.ui.discovery.presenter.ReleaseFormulaPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/19.
 */

public class ReleaseFormulaActivity extends BaseActivity<ReleaseFormulaPresenter> implements ReleaseFormulaContract.ReleaseFormulaView {

    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_crop_name)
    @BindView(R.id.et_crop_name)
    EditText mEtCropName;
    @Order(2)
    @NotEmpty(messageResId = R.string.please_input_disease_name)
    @BindView(R.id.et_disease_name)
    EditText mEtDiseaseName;
    @Order(3)
    @NotEmpty(messageResId = R.string.please_input_unique)
    @BindView(R.id.et_method)
    EditText mEtMethod;
    @BindView(R.id.tv_submit)
    RoundTextView mTvSubmit;
    private DefaultValidator mValidator;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, ReleaseFormulaActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_formula;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        id = getIntent().getIntExtra("id", 0);
        mValidator = new DefaultValidator(this);
        mValidator.clicked(mTvSubmit).succeeded(() -> {
            String cropName = mEtCropName.getText().toString();
            String diseaseName = mEtDiseaseName.getText().toString();
            String method = mEtMethod.getText().toString();
            mPresenter.requestReleaseFormula(cropName, diseaseName, method, id);
        });
    }

    @Override
    public void complete() {

    }

    @Override
    public void releaseFormulaSuccess() {
        EventBus.getDefault().post(new ReleaseFormulaEvent());
        finish();
    }
}
