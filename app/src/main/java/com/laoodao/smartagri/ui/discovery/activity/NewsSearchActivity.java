package com.laoodao.smartagri.ui.discovery.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.NewsAdapter;
import com.laoodao.smartagri.ui.discovery.contract.NewsSearchContract;
import com.laoodao.smartagri.ui.discovery.presenter.NewsSearchPresenter;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/20.
 */

public class NewsSearchActivity extends BaseXRVActivity<NewsSearchPresenter> implements NewsSearchContract.NewsSearchView {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    private String kw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_search;
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
        initAdapter(NewsAdapter.class);
        mEtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.clear();
                kw = s.toString();
                if (s.length() > 0) {
                    onRefresh();
                } else {
                    gone(mMultiStateView);
                    gone(mRecyclerView);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestNewsSearch(0, page, kw);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestNewsSearch(0, page, kw);
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                mPresenter.requestNewsSearch(0, 1, kw);
                break;
        }
    }

    @Override
    public void newsSearchSuccess() {
        if (!TextUtils.isEmpty(kw)) {
            visible(mMultiStateView);
            visible(mRecyclerView);
        }

    }

//    @Override
//    public void newsSearchSuccess(List<News> newsList, boolean isRefresh) {
//        visible(mMultiStateView);
//        mAdapter.addAll(newsList, isRefresh);
//    }
}
