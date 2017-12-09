package com.laoodao.smartagri.ui.discovery.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.ConcernCropPriceAdapter;
import com.laoodao.smartagri.ui.discovery.contract.ConcernCropPriceContract;
import com.laoodao.smartagri.ui.discovery.presenter.ConcernCropPricePresenter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ConcernCropPriceActivity extends BaseXRVActivity<ConcernCropPricePresenter> implements ConcernCropPriceContract.ConcernCropPriceView {


    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.tv_no_result)
    TextView mTvNoResult;
    @BindView(R.id.rcy_search_result)
    RecyclerView mRcySearchResult;
    @BindView(R.id.ll_noteworthy)
    LinearLayout mLlNoteworthy;
    @BindView(R.id.fl_empty)
    FrameLayout mFlEmpty;

    private ActionSheetDialog sheetDialog;
    private ConcernCropPriceAdapter mSearcAdapter;
    private List<PriceWonder> mPriceWonders = new ArrayList<>();
    private List<PriceWonder> mSearchWonder = new ArrayList<>();
    private List<PriceWonder> mOriginalList = new ArrayList<>();


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
        return R.layout.activity_concern_crops_price;
    }

    @Override
    protected void configViews() {

        mAdapter = new ConcernCropPriceAdapter(this, ((positoin, isWonder) -> {
            PriceWonder wonder = (PriceWonder) mAdapter.getItem(positoin);
            initDialog(isWonder, wonder, mAdapter);
        }));
        mSearcAdapter = new ConcernCropPriceAdapter(this, ((positoin, isWonder) -> {
            PriceWonder wonder = mSearcAdapter.getItem(positoin);
            initDialog(isWonder, wonder, mSearcAdapter);
        }));
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), UiUtils.dip2px(1), 0, 0));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRcySearchResult.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), UiUtils.dip2px(1), 0, 0));
        mRcySearchResult.setHasFixedSize(true);
        mRcySearchResult.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRcySearchResult.setAdapter(mSearcAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        initAdapter();
        keyWordChange();
        onRefresh();
    }

    public void initDialog(int isWonder, PriceWonder wonder, BaseAdapter adapter) {
        final String[] stringItems = {"确定"};
        if (isWonder == 0) {
            wonder.isWonder = 1;
            mPresenter.requestAdd(wonder.id);
            adapter.notifyDataSetChanged();
        } else {
            if (sheetDialog == null) {
                sheetDialog = new ActionSheetDialog(this, stringItems, null);
            }
            sheetDialog.title("确定取消关注" + wonder.name + "吗?")
                    .titleTextSize_SP(14.5f)
                    .titleTextColor(ContextCompat.getColor(this, R.color.common_h2))
                    .itemTextColor(ContextCompat.getColor(this, R.color.common_h1))
                    .lvBgColor(ContextCompat.getColor(this, R.color.white))
                    .titleBgColor(ContextCompat.getColor(this, R.color.white))
                    .layoutAnimation(null)
                    .itemTextSize(16f)
                    .showAnim(null)
                    .dismissAnim(null)
                    .show();
            sheetDialog.setOnOperItemClickL((parent, view, position, id) -> {
                wonder.isWonder = 0;
                mPresenter.requestAdd(wonder.id);
                adapter.notifyDataSetChanged();
                sheetDialog.dismiss();
            });
        }
    }

    public void keyWordChange() {
        mEtKeyword.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //priceAdapter.clear();
                String keyWord = s.toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    mTvSearch.setVisibility(View.GONE);
//                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
//                    mEtKeyword.setLayoutParams(params);
//                    mEtKeyword.setCompoundDrawables(ContextCompat.getDrawable(context, R.mipmap.ic_search_gray), null, null, null);
                    mLlNoteworthy.setVisibility(View.VISIBLE);
                } else {
                    mLlNoteworthy.setVisibility(View.GONE);
                    mTvSearch.setVisibility(View.VISIBLE);
                    mTvSearch.setText(Html.fromHtml(getResources().getString(R.string.serch, keyWord)));
                }
                mTvNoResult.setVisibility(View.GONE);
                mRcySearchResult.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestList();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
    }


    @Override
    public void SuccessList(List<PriceWonder> priceWonders) {
        //  Log.e(")))))))))", mPriceWonders.size() + "SuccessList");
//        mPriceWonders.clear();
//        mPriceWonders.addAll(priceWonders);
        mOriginalList.clear();
        mOriginalList.addAll(priceWonders);
        removeMyWonder(priceWonders);
    }

    public void removeMyWonder(List<PriceWonder> wonder) { //移除已经关注的
        if (wonder.size() != 0) {
            for (int i = wonder.size() - 1; i >= 0; i--) {
                if (wonder.get(i).isWonder == 1) {
                    wonder.remove(i);
                }
            }
            if (wonder.size() == 0) {
                mFlEmpty.setVisibility(View.VISIBLE);
                mMultiStateView.setVisibility(View.VISIBLE);
            } else {
                if (mMultiStateView != null) {
                    mFlEmpty.setVisibility(View.GONE);
                    mMultiStateView.setVisibility(View.VISIBLE);
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            }
            mAdapter.addAll(wonder, true);
        }
    }

    @Override
    public void successAdd() {

    }

    @OnClick({R.id.et_keyword, R.id.tv_search, R.id.fl_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_search:
                mEtKeyword.setFocusable(true);
                mEtKeyword.setFocusableInTouchMode(true);
                mEtKeyword.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
            case R.id.et_keyword:
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                mEtKeyword.setLayoutParams(params);
//                mEtKeyword.setCompoundDrawables(null, null, null, null);
                break;
            case R.id.tv_search:
                mSearchWonder.clear();
                mPriceWonders.clear();
                mPriceWonders.addAll(mOriginalList);
                mTvSearch.setVisibility(View.GONE);
                DeviceUtils.hideSoftKeyboard(view.getContext(), mTvSearch);
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                if (mPriceWonders.size() != 0) {
                    for (int i = mPriceWonders.size() - 1; i >= 0; i--) {
                        if (mPriceWonders.get(i).name.contains(mEtKeyword.getText().toString().trim())) {
                            mSearchWonder.add(mPriceWonders.get(i));
                            mPriceWonders.remove(i);
                        } else if (mPriceWonders.get(i).isWonder == 1) {
                            mPriceWonders.remove(i);

                        }

                    }
                }
                searchSuccess(mSearchWonder);
                break;
        }
    }

    public void searchSuccess(List<PriceWonder> wonder) {
        Observable.timer(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aLong -> {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                });
        if (wonder != null && wonder.size() != 0) {
            mSearcAdapter.addAll(wonder, true);
            mAdapter.addAll(mPriceWonders, true);
            mRcySearchResult.setVisibility(View.VISIBLE);
            mTvNoResult.setVisibility(View.GONE);
        } else {
            mRcySearchResult.setVisibility(View.GONE);
            mTvNoResult.setVisibility(View.VISIBLE);
        }
        mLlNoteworthy.setVisibility(View.VISIBLE);
    }
}