package com.laoodao.smartagri.ui.user.activity;

import com.flyco.dialog.widget.MaterialDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.IntegralRule;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.IntegralRuleAdapter;
import com.laoodao.smartagri.ui.user.contract.IntegralRuleContract;
import com.laoodao.smartagri.ui.user.presenter.IntegralRulePresenter;

import java.util.List;

/**
 * Created by WORK on 2017/5/10.
 */

public class IntegralRuleActivity extends BaseXRVActivity<IntegralRulePresenter> implements IntegralRuleContract.IntegralRuleView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_rule;
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
        initAdapter(IntegralRuleAdapter.class);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mPresenter.requestIntegralRule();
    }

    @Override
    public void integralRule(List<IntegralRule> list) {
        mAdapter.addAll(list);
    }
}
