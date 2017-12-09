package com.laoodao.smartagri.ui.market.fragment;

import android.view.View;
import android.widget.TextView;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.ui.market.activity.MyBrowseActivity;
import com.laoodao.smartagri.ui.market.activity.MyCollectionActivity;
import com.laoodao.smartagri.ui.market.activity.MyReleaseActivity;
import com.laoodao.smartagri.ui.market.contact.MySupplyDemandContact;
import com.laoodao.smartagri.ui.market.presenter.MySupplyDemandPresenter;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的-》供求
 */

public class MySupplyDemandFragment extends BaseFragment<MySupplyDemandPresenter> implements MySupplyDemandContact.MySupplyDemandView {
    @BindView(R.id.tv_my_release)
    TextView mTvMyRelease;
    @BindView(R.id.tv_my_collection)
    TextView mTvMyCollection;
    @BindView(R.id.tv_browse_record)
    TextView mTvBrowseRecord;

    @Override
    public void complete() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_supply_demand;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.tv_my_release, R.id.tv_my_collection, R.id.tv_browse_record})
    public void onClick(View view) {
        isLogin(view);
    }

    private void isLogin(View view) {
        if (!Global.getInstance().isLoggedIn()) {
            UiUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.tv_my_release:
                UiUtils.startActivity(getActivity(), MyReleaseActivity.class);
                break;
            case R.id.tv_my_collection:
                UiUtils.startActivity(getActivity(), MyCollectionActivity.class);
                break;
            case R.id.tv_browse_record:
                UiUtils.startActivity(getActivity(), MyBrowseActivity.class);
                break;
        }
    }


}
