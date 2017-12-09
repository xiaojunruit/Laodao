package com.laoodao.smartagri.ui.discovery.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.PriceQuotationAdapter;
import com.laoodao.smartagri.ui.discovery.contract.PriceQuotationSearchContract;
import com.laoodao.smartagri.ui.discovery.presenter.PriceQuotationSearchPresenter;
import com.laoodao.smartagri.ui.qa.adapter.QuestionSearchAdapter;
import com.laoodao.smartagri.ui.qa.adapter.SearchResultAdapter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.TagGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/8/1.
 */

public class PriceQuotationSearchActivity extends BaseXRVActivity<PriceQuotationSearchPresenter> implements PriceQuotationSearchContract.PriceQuotationSearchView, TextWatcher, XRecyclerView.LoadingListener {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.tg_hot)
    TagGroup mTgHot;
    @BindView(R.id.sv_hot)
    ScrollView mSvHot;
    private SelectLocation currentLocation;
    private String pos = "";
    private int cityId;
    private String keywork = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_quotation_search;
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
        currentLocation = Global.getInstance().getCurrentLocation();
        if (currentLocation != null) {
            pos = currentLocation.longitude + "," + currentLocation.latitude;
        }
        mEtKeyword.addTextChangedListener(this);
        initAdapter(PriceQuotationAdapter.class);
        mPresenter.getHotKeyword();
    }

    @Override
    public void showHotKeyword(List<Keyword> data) {
        mTgHot.setList(data);
        mTgHot.setOnTagItemClickListener((view, position, tag) -> {
            Keyword keyword = (Keyword) tag;
            mEtKeyword.setText(keyword.kw);
            mEtKeyword.setSelection(mEtKeyword.getText().length());
            onRefresh();
        });
    }

    @Override
    public void sucess() {
        if (TextUtils.isEmpty(keywork)) {
            gone(mMultiStateView);
            visible(mSvHot);
        } else {
            visible(mMultiStateView);
            gone(mSvHot);
        }

    }

//    @Override
//    public void showPriceList(List<PriceQuotation> items, boolean isRefresh) {
//        mAdapter.addAll(items, isRefresh);
//        visible(mMultiStateView);
//        visible(mRecyclerView);
//    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                onRefresh();
                DeviceUtils.hideSoftKeyboard(this, mEtKeyword);
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
        keywork = s.toString();
        if (TextUtils.isEmpty(s.toString())) {
            gone(mMultiStateView);
            visible(mSvHot);
        } else {
            gone(mSvHot);
            onRefresh();
//            if (mMultiStateView != null) {
//                mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).setOnClickListener(v -> {
//                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
//                    onRefresh();
//                });
//                mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).setOnClickListener(v -> {
//                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
//                    onRefresh();
//                });
//            }
        }
    }

    @Override
    public void onRefresh() {
        String keyword = mEtKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword))
            return;
        mPresenter.requestData(0, page, 0, keyword, pos, 0);
    }

    @Override
    public void onLoadMore() {
        String keyword = mEtKeyword.getText().toString();
        mPresenter.requestData(0, page, 0, keyword, pos, 0);
    }

}
