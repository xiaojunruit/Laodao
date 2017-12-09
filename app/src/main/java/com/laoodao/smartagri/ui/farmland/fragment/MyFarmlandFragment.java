package com.laoodao.smartagri.ui.farmland.fragment;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Farmland;
import com.laoodao.smartagri.bean.base.Pagination;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.FarmlandChangeEvent;
import com.laoodao.smartagri.ui.farmland.activity.AddFarmlandActivity;
import com.laoodao.smartagri.ui.farmland.adapter.MyFarmlandAdapter;
import com.laoodao.smartagri.ui.farmland.contract.MyFarmlandContract;
import com.laoodao.smartagri.ui.farmland.presenter.MyFarmlandPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/23.
 */

public class MyFarmlandFragment extends BaseXRVFragment<MyFarmlandPresenter> implements MyFarmlandContract.MyFarmlandView {

    @Override
    public int getLayoutResId() {
        return R.layout.common_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        mHeader = new FramlandHeader(getContext());
        initAdapter(MyFarmlandAdapter.class);
        onRefresh();
    }


    class FramlandHeader extends BaseHeaderView {

        @BindView(R.id.add_farmland)
        LinearLayout mAddFarmland;
        @BindView(R.id.view_left)
        View mViewLeft;

        public FramlandHeader(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_add_framland;
        }

        @OnClick(R.id.add_farmland)
        public void onClick() {
            UiUtils.startActivity(AddFarmlandActivity.class);
        }

    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void farmlandChange(FarmlandChangeEvent event) {
        onRefresh();
    }


    @Override
    public void showFarmlandList(Pagination<Farmland> data, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(data.items);
        FramlandHeader header = ((FramlandHeader) mHeader);
        header.mViewLeft.setVisibility(data.items.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getFarmlandList(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getFarmlandList(page);
    }

}
