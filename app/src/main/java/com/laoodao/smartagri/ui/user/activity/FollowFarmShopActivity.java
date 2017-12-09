package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ejz.multistateview.MultiStateView;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.WonderStore;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.FollowFarmShopAdapter;
import com.laoodao.smartagri.ui.user.contract.FollowFarmShopContract;
import com.laoodao.smartagri.ui.user.presenter.FollowFarmShopPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;

public class FollowFarmShopActivity extends BaseXRVActivity<FollowFarmShopPresenter> implements FollowFarmShopContract.FollowFarmShopView {
    private ActionSheetDialog sheetDialog;
    private String mMemberId;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public static void start(Context context, String memberId) {
        Bundle bundle = new Bundle();
        bundle.putString("memberId", memberId);
        UiUtils.startActivity(context, FollowFarmShopActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_xrecyclerview;
    }

    @Override
    protected void configViews() {
        initDialog();
        mMemberId = getIntent().getStringExtra("memberId");
        mAdapter = new FollowFarmShopAdapter(this, ((positoin, isWonder) -> {
            WonderStore store = (WonderStore) mAdapter.getItem(positoin);
            if (isWonder == 0) {
                store.isWonder = 1;
                mAdapter.notifyDataSetChanged();
                mPresenter.AddOrDelFollow(store.StoreId);
            } else {
                sheetDialog.show();
                sheetDialog.setOnOperItemClickL((parent, view, position, id) -> {
                    store.isWonder = 0;
                    mAdapter.notifyDataSetChanged();
                    mPresenter.AddOrDelFollow(store.StoreId);
                    sheetDialog.dismiss();
                });
            }
        }));
        initAdapter();
        mRecyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), UiUtils.dip2px(1)));
        onRefresh();
    }

    private void initDialog() {
        sheetDialog = new ActionSheetDialog(this, new String[]{"确定"}, null);
        sheetDialog.title("确定取消关注吗?")
                .titleTextSize_SP(16f)
                .titleTextColor(ContextCompat.getColor(this, R.color.common_h1))
                .itemTextColor(ContextCompat.getColor(this, R.color.common_h1))
                .lvBgColor(ContextCompat.getColor(this, R.color.white))
                .titleBgColor(ContextCompat.getColor(this, R.color.white))
                .itemTextSize(16f)
                .cancelText(ContextCompat.getColor(this, R.color.common_h1))
                .cancelTextSize(16f)
                .showAnim(null)
                .layoutAnimation(null)
                .dismissAnim(null);
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestList( mMemberId,page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestList( mMemberId,page);
    }
}