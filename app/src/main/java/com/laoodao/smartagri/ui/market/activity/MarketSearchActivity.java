package com.laoodao.smartagri.ui.market.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.ui.market.adapter.MarketMySearchAdapter;
import com.laoodao.smartagri.ui.market.adapter.MarketSearchAdapter;
import com.laoodao.smartagri.ui.market.contact.MarketSearchContract;
import com.laoodao.smartagri.ui.market.presenter.MarketSearchPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.TagGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MarketSearchActivity extends BaseActivity<MarketSearchPresenter> implements MarketSearchContract.MarketSearchView, TextWatcher, XRecyclerView.LoadingListener {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.tg_hot)
    TagGroup mTgHot;
    @BindView(R.id.recommend_recyclerview)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.sv_hot)
    ScrollView mSvHot;
    private MarketMySearchAdapter mAdapter;
    private MarketSearchAdapter mResultAdapter;
    int page = 1;
    private int mPosition;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_question_search;
    }

    @Override
    protected void configViews() {
        mEtKeyword.addTextChangedListener(this);
        initRecyclerView();
        mPresenter.getHostWords();
        mPresenter.getMySearch();
    }

    private void initRecyclerView() {
        //我的搜索历史
        mAdapter = new MarketMySearchAdapter(this);
        mRecommendRecyclerView.setAdapter(mAdapter);
        mRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter.setOnItemClickListener(position -> {
            Keyword item = mAdapter.getItem(position);
            mEtKeyword.setText(item.kw);
            mEtKeyword.setSelection(mEtKeyword.getText().length());
            onRefresh();
        });
        mAdapter.setOnDelClickListener((id, position) -> {
            mPosition = position;
            mPresenter.requestDelHistory(id);
        });
        //热搜
        mResultAdapter = new MarketSearchAdapter(this);
        mRecyclerView.setAdapter(mResultAdapter);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.transparent), UiUtils.dip2px(10)));
        mResultAdapter.setOnItemClickListener(position -> {
            mPresenter.requestAddSearch(mResultAdapter.getItem(position).title);
            if (mResultAdapter.getItem(position).type == 1) {
                SupplyDetailsActivity.start(this, "供销详情", mResultAdapter.getItem(position).id);
            } else {
                BuyDetailsActivity.start(this, "求购详情", mResultAdapter.getItem(position).id);
            }
        });
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

    @Override
    public void complete() {
        mRecyclerView.complete();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            gone(mMultiStateView);
            gone(mRecyclerView);
            visible(mSvHot);
        } else {
            gone(mSvHot);
            onRefresh();

        }


    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                onRefresh();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEtKeyword.getWindowToken(), 0);
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        String keyword = mEtKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword))
            return;
        mPresenter.searchSupply(page, keyword);
    }

    @Override
    public void onLoadMore() {
        page++;
        String keyword = mEtKeyword.getText().toString();
        mPresenter.searchSupply(page, keyword);
    }

    @Override
    public void noMore(boolean noMore) {
        mRecyclerView.noMoreLoading();
    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            if (mResultAdapter.getAllData() == null) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
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


    @Override
    public void showSupplyList(List<Supplylists> items, boolean isRefresh) {
        mResultAdapter.addAll(items, isRefresh);
        visible(mMultiStateView);
        visible(mRecyclerView);
//        if (items.size() > 0) {
//            visible(mRecyclerView);
//        } else {
//            gone(mRecyclerView);
//        }
    }

    @Override
    public void Search() {
        mPresenter.getMySearch();
    }

    @Override
    public void showHostWords(List<Keyword> data) {
        mTgHot.setList(data);
        mTgHot.setOnTagItemClickListener((view, position, tag) -> {
            Keyword keyword = (Keyword) tag;
            mEtKeyword.setText(keyword.kw);
            mEtKeyword.setSelection(mEtKeyword.getText().length());
        });
        onRefresh();
    }

    @Override
    public void mySearch(List<Keyword> data) {
        mAdapter.addAll(data);
    }

    @Override
    public void deleteHistory() {
        mAdapter.remove(mPosition);
        mAdapter.notifyDataSetChanged();
    }


}