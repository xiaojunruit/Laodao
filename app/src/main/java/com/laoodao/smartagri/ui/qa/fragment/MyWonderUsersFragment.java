package com.laoodao.smartagri.ui.qa.fragment;

import android.support.v4.content.ContextCompat;

import com.flyco.dialog.widget.ActionSheetDialog;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.WonderUser;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.ui.qa.contract.MyWonderUsersContract;
import com.laoodao.smartagri.ui.qa.presenter.MyWonderUsersPresenter;
import com.laoodao.smartagri.ui.user.adapter.FansAdapter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyWonderUsersFragment extends BaseXRVFragment<MyWonderUsersPresenter> implements MyWonderUsersContract.MyWonderUsersView {

    private int cid;
    private ActionSheetDialog sheetDialog;
    private String[] stringItems = {"确定"};

    @Override
    public int getLayoutResId() {
        return R.layout.common_ft_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    public void configViews() {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        cid = Integer.parseInt(Global.getInstance().getUserInfo().cid);
        onRefresh();
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(getContext(), R.color.common_divider_narrow), UiUtils.dip2px(1), 0, 0));
        sheetDialog = new ActionSheetDialog(getContext(), stringItems, null);
        sheetDialog.title("确定不在关注该用户吗?")
                .titleTextSize_SP(14.5f)
                .titleTextColor(ContextCompat.getColor(getContext(), R.color.common_h1))
                .itemTextColor(ContextCompat.getColor(getContext(), R.color.common_h1))
                .lvBgColor(ContextCompat.getColor(getContext(), R.color.white))
                .titleBgColor(ContextCompat.getColor(getContext(), R.color.white))
                .itemTextSize(16f)
                .showAnim(null)
                .layoutAnimation(null)
                .cancelText(ContextCompat.getColor(getContext(), R.color.common_h1))
                .dismissAnim(null);

        mAdapter = new FansAdapter(getContext(), ((positoin, isWonder) -> {
            WonderUser wonder = (WonderUser) mAdapter.getItem(positoin);
            if (isWonder == 1) {
                sheetDialog.show();
                wonder.isWonder = 1;
            } else {
                wonder.isWonder = 0;
                mAdapter.notifyDataSetChanged();
                mPresenter.Follow(wonder.memberId);
            }
            sheetDialog.setOnOperItemClickL((parent, view, position, id) -> {
                mAdapter.notifyDataSetChanged();
                mPresenter.Follow(wonder.memberId);
                sheetDialog.dismiss();
            });
        }));
        initAdapter();
    }

    @Override
    public void FollowSuccess() {

    }

    @Override
    public void onRefresh() {
        mPresenter.requestList(cid, page, 1);
        super.onRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.requestList(cid, page, 1);
        super.onLoadMore();
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
    }
}