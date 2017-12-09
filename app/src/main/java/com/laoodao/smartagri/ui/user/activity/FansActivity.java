package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ejz.multistateview.MultiStateView;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.WonderUser;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.FansAdapter;
import com.laoodao.smartagri.ui.user.contract.FansContract;
import com.laoodao.smartagri.ui.user.presenter.FansPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;

public class FansActivity extends BaseXRVActivity<FansPresenter> implements FansContract.FansView {
    private int mMemberId;
    private int mType;
    private ActionSheetDialog sheetDialog;
    private String[] stringItems = {"确定"};

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_xrecyclerview;
    }

    public static void start(Context context, int memberId, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("memberId", memberId);
        bundle.putInt("type", type);
        UiUtils.startActivity(context, FansActivity.class, bundle);
    }

    @Override
    protected void configViews() {
        mType = getIntent().getIntExtra("type", 1);
        mMemberId = getIntent().getIntExtra("memberId", 0);
        if (mType == 1) setTitle("关注的用户");
        else setTitle("粉丝");
        sheetDialog = new ActionSheetDialog(this, stringItems, null);
        sheetDialog.title("确定不在关注该用户吗?")
                .titleTextSize_SP(14.5f)
                .titleTextColor(ContextCompat.getColor(this, R.color.common_h1))
                .itemTextColor(ContextCompat.getColor(this, R.color.common_h1))
                .lvBgColor(ContextCompat.getColor(this, R.color.white))
                .titleBgColor(ContextCompat.getColor(this, R.color.white))
                .itemTextSize(16f)
                .showAnim(null)
                .layoutAnimation(null)
                .cancelText(ContextCompat.getColor(this, R.color.common_h1))
                .dismissAnim(null);
        mAdapter = new FansAdapter(this, ((positoin, isWonder) -> {
            WonderUser wonder = (WonderUser) mAdapter.getItem(positoin);
            if (isWonder == 0) {
                wonder.isWonder = 1;
                mAdapter.notifyDataSetChanged();
                mPresenter.Follow(wonder.memberId);
            } else {
                wonder.isWonder = 0;
                sheetDialog.show();
            }
            sheetDialog.setOnOperItemClickL((parent, view, position, id) -> {
                mAdapter.notifyDataSetChanged();
                mPresenter.Follow(wonder.memberId);
                sheetDialog.dismiss();
            });
        }));
        initAdapter();
        onRefresh();
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), UiUtils.dip2px(1), 0, 0));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestList(mMemberId, page, mType);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestList(mMemberId, page, mType);
    }
}