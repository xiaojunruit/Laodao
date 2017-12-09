package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.InviteList;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.InviteListAdapter;
import com.laoodao.smartagri.ui.user.contract.InviteListContract;
import com.laoodao.smartagri.ui.user.presenter.InviteListPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class InviteListActivity extends BaseXRVActivity<InviteListPresenter> implements InviteListContract.IniteListView {

    private String inviteNum = "";

    public static void start(Context context, String inviteNum) {
        Bundle bundle = new Bundle();
        bundle.putString("inviteNum", inviteNum);
        UiUtils.startActivity(context, InviteListActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    class InviteListHeader extends BaseHeaderView {

        @BindView(R.id.tv_count)
        TextView mTvCount;

        public InviteListHeader(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_invite;
        }
    }

    @Override
    protected void configViews() {
        inviteNum = getIntent().getStringExtra("inviteNum");
        mHeader = new InviteListHeader(this);
        InviteListHeader header = ((InviteListHeader) mHeader);
        header.mTvCount.setText(inviteNum);
        initAdapter(InviteListAdapter.class);
        onRefresh();

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestInviteListData(page);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestInviteListData(page);
    }
}
