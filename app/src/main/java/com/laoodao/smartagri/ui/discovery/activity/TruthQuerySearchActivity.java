package com.laoodao.smartagri.ui.discovery.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.TruthQuery;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.TruthSearchAdapter;
import com.laoodao.smartagri.ui.discovery.contract.TruthQuerySearchContract;
import com.laoodao.smartagri.ui.discovery.presenter.TruthQuerySearchPresenter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/3.
 */

public class TruthQuerySearchActivity extends BaseXRVActivity<TruthQuerySearchPresenter> implements TruthQuerySearchContract.TruthQuerySearchView, TextWatcher {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_search)
    TextView mTvSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_truth_query_search;
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
        initAdapter(TruthSearchAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getApplicationContext(), R.color.common_divider_narrow), UiUtils.dip2px(1), 45, 0);
        mRecyclerView.addItemDecoration(decoration);
        mEtKeyword.addTextChangedListener(this);
        if (mMultiStateView != null) {
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
                onRefresh();
            });
            mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                onRefresh();
            });
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                DeviceUtils.hideSoftKeyboard(this, mEtKeyword);
                finish();
                break;
            case R.id.tv_search:
                String keyword = mEtKeyword.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    UiUtils.makeText("请输入搜索信息");
                    return;
                }
                visible(mMultiStateView);
                onRefresh();
                break;
        }

    }


    @Override
    public void ShowSearch(List<TruthQuery> truthQuery, boolean isRefresh) {
        mAdapter.addAll(truthQuery, isRefresh);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestSearch(page, mEtKeyword.getText().toString());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestSearch(page, mEtKeyword.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mAdapter.clear();
        if (TextUtils.isEmpty(s.toString())) {
            gone(mMultiStateView);
        } else {
            visible(mMultiStateView);
            onRefresh();
        }
    }
}
