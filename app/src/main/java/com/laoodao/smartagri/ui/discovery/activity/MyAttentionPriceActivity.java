package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ejz.multistateview.MultiStateView;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.MyAttentionPriceAdapter;
import com.laoodao.smartagri.ui.discovery.contract.MyAttentionPriceContract;
import com.laoodao.smartagri.ui.discovery.presenter.MyAttentionPricePresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

public class MyAttentionPriceActivity extends BaseXRVActivity<MyAttentionPricePresenter> implements MyAttentionPriceContract.MyAttentionPriceView {

    @BindView(R.id.ll_prompt)
    LinearLayout mLlPrompt;
    private ActionSheetDialog sheetDialog;
    private int mId;
    private int mMemberId;
    private String[] stringItems = {"确定"};

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, int id, int memberId) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("memberId", memberId);
        UiUtils.startActivity(context, MyAttentionPriceActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_attention_price;
    }

    @Override
    protected void configViews() {
        mMemberId = getIntent().getIntExtra("memberId", 0);
        mId = getIntent().getIntExtra("id", 0);
        if (mId == 1) {
            setTitle("关注的价格行情");
        }
        sheetDialog = new ActionSheetDialog(this, stringItems, null);
        sheetDialog
                .titleTextSize_SP(14.5f)
                .titleTextColor(ContextCompat.getColor(this, R.color.common_h2))
                .itemTextColor(ContextCompat.getColor(this, R.color.common_h1))
                .lvBgColor(ContextCompat.getColor(this, R.color.white))
                .titleBgColor(ContextCompat.getColor(this, R.color.white))
                .itemTextSize(16f)
                .showAnim(null)
                .layoutAnimation(null)
                .dismissAnim(null);
        mAdapter = new MyAttentionPriceAdapter(this, ((positoin, isWonder) -> {
            PriceWonder wonder = (PriceWonder) mAdapter.getItem(positoin);
            if (isWonder == 0) {
                wonder.isWonder = 1;
                mPresenter.requestAdd(wonder.id);
                mAdapter.notifyDataSetChanged();
            } else {
                sheetDialog.title("确定取消关注" + wonder.name + "吗?");
                sheetDialog.show();
                sheetDialog.setOnOperItemClickL((parent, view, position, id) -> {
                    wonder.isWonder = 0;
                    mAdapter.notifyDataSetChanged();
                    mPresenter.requestAdd(wonder.id);
                    sheetDialog.dismiss();
                });

            }
        }));
        initAdapter();
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), UiUtils.dip2px(1), 0, 0));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    public void SuccessList(List<PriceWonder> wonders) {
        for (PriceWonder wonder : wonders) {
            wonder.isWonder = 1;
        }
        if (wonders.size() == 0) {
            mLlPrompt.setVisibility(View.VISIBLE);
        } else {
            mLlPrompt.setVisibility(View.GONE);
        }
        mAdapter.addAll(wonders, true);
    }

    @Override
    public void SuccessWonderPrice(List<PriceWonder> priceWonders) {
        mAdapter.addAll(priceWonders, true);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (mId == 1) {
            mPresenter.requestWonderPrice(mMemberId);
        } else {
            mPresenter.requestList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            UiUtils.startActivity(ConcernCropPriceActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onEmpty() {
        mLlPrompt.setVisibility(View.VISIBLE);
        if (mMultiStateView != null) {
            mMultiStateView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setVisibility(View.VISIBLE);
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }
}