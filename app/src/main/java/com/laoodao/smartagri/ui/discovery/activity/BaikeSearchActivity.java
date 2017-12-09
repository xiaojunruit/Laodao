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
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.BaikeAdapter;
import com.laoodao.smartagri.ui.discovery.contract.BaikeSearchContract;
import com.laoodao.smartagri.ui.discovery.presenter.BaikeSearchPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaikeSearchActivity extends BaseXRVActivity<BaikeSearchPresenter> implements BaikeSearchContract.BaikeSearchView, TextWatcher {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_search)
    TextView mTvSearch;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_truth_query_search;

    }

    @Override
    protected void configViews() {
        initAdapter(BaikeAdapter.class);
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

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (TextUtils.isEmpty(mEtKeyword.getText().toString())) return;
        mPresenter.requestList(page, mEtKeyword.getText().toString());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (TextUtils.isEmpty(mEtKeyword.getText().toString())) return;

        mPresenter.requestList(page, mEtKeyword.getText().toString());
    }


    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                if (TextUtils.isEmpty(mEtKeyword.getText().toString())) {
                    UiUtils.makeText("请输入搜索内容");
                    return;
                }
                onRefresh();
                break;
        }
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


    @Override
    public void showList() {
        if (!TextUtils.isEmpty(mEtKeyword.getText().toString()))
            visible(mMultiStateView);

    }
}