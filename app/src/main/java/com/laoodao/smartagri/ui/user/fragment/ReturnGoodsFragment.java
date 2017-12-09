package com.laoodao.smartagri.ui.user.fragment;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.ReturnGoodsAdapter;
import com.laoodao.smartagri.ui.user.contract.ReturnGoodsContract;
import com.laoodao.smartagri.ui.user.presenter.ReturnGoodsPresenter;

/**
 * Created by WORK on 2017/7/5.
 */

public class ReturnGoodsFragment extends BaseXRVFragment<ReturnGoodsPresenter> implements ReturnGoodsContract.ReturnGoodsView {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_return_goods;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        initAdapter(ReturnGoodsAdapter.class);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.returnGoodsList(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.returnGoodsList(page);
    }
}
