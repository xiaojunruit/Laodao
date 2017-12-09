package com.laoodao.smartagri.ui.qa.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.DeleteHistoryEvent;
import com.laoodao.smartagri.ui.qa.adapter.QuestionSearchAdapter;
import com.laoodao.smartagri.ui.qa.adapter.SearchResultAdapter;
import com.laoodao.smartagri.ui.qa.contract.QuestionSearchContract;
import com.laoodao.smartagri.ui.qa.presenter.QuestionSearchPresenter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.TagGroup;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionSearchActivity extends BaseActivity<QuestionSearchPresenter> implements QuestionSearchContract.QuestionSearchView, TextWatcher, XRecyclerView.LoadingListener {


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
    @BindView(R.id.sv_hot)
    ScrollView mSvHot;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private QuestionSearchAdapter mAdapter;
    private SearchResultAdapter mResultAdapter;
    int page = 1;
    private String keyword = "";

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
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
        mPresenter.getHotKeyword();
        mPresenter.getHistorySearch();
    }

    private void initRecyclerView() {
        mAdapter = new QuestionSearchAdapter(this);
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
        mResultAdapter = new SearchResultAdapter(this);
        mRecyclerView.setAdapter(mResultAdapter);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.transparent), UiUtils.dip2px(10)));
    }


    @Subscribe
    public void onDeleteHistory(DeleteHistoryEvent event) {
        mPresenter.deleteHistory(event.id);
    }

    @Override
    public void complete() {
        mRecyclerView.complete();
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
    public void showHistory(List<Keyword> data) {
        mAdapter.addAll(data, true);
    }

    @Override
    public void showQuestionList(List<Question> items, boolean isRefresh) {
        mResultAdapter.addAll(items, isRefresh);
        if (TextUtils.isEmpty(keyword)) {
            gone(mMultiStateView);
            gone(mRecyclerView);
            visible(mSvHot);
        } else {
            visible(mMultiStateView);
            visible(mRecyclerView);
        }

    }

    @Override
    public void delteSuccess() {
        mPresenter.getHistorySearch();
    }

    /**
     * 添加成功
     */
    @Override
    public void addSearchSuccess() {
        mPresenter.getHistorySearch();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                addSearch();
                onRefresh();
                DeviceUtils.hideSoftKeyboard(this, mEtKeyword);
                break;
        }
    }

    private void addSearch() {
        String keyword = mEtKeyword.getText().toString();
        mPresenter.addSearch(keyword);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        keyword = s.toString();
        if (TextUtils.isEmpty(s.toString())) {
            gone(mMultiStateView);
            gone(mRecyclerView);
            visible(mSvHot);
        } else {
            gone(mSvHot);
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
    }

    @Override
    public void onRefresh() {
        page = 1;
        String keyword = mEtKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword))
            return;
        mPresenter.searchQuestion(page, keyword);
    }

    @Override
    public void onLoadMore() {
        page++;
        String keyword = mEtKeyword.getText().toString();
        mPresenter.searchQuestion(page, keyword);
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
    protected boolean enableEventBus() {
        return true;
    }
}