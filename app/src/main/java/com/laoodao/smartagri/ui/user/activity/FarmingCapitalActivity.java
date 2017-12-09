package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.FarmingCapitalContract;
import com.laoodao.smartagri.ui.user.dialog.FarmingCapitalSelectDialog;
import com.laoodao.smartagri.ui.user.fragment.MyOrderFragment;
import com.laoodao.smartagri.ui.user.fragment.PaymentHistoryFragment;
import com.laoodao.smartagri.ui.user.fragment.ReturnGoodsFragment;
import com.laoodao.smartagri.ui.user.presenter.FarmingCapitalPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/28.
 */

public class FarmingCapitalActivity extends BaseActivity<FarmingCapitalPresenter> implements FarmingCapitalContract.FramingCapitalView, View.OnClickListener {


    private FragmentChangeManager mFragmentChangeManager;
    private FarmingCapitalSelectDialog mDialog;
    private String title;

    @Override
    public void complete() {

    }

    public static void start(Context context, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        UiUtils.startActivity(context, FarmingCapitalActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_farming_capital;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        title = getIntent().getStringExtra("title");
        setTitle(title);
        setToolbarTitle(true);
        mToolbarTitle.setOnClickListener(this);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyOrderFragment());
        fragments.add(new PaymentHistoryFragment());
        fragments.add(new ReturnGoodsFragment());
        mFragmentChangeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.content, fragments);
        mFragmentChangeManager.setFragments(0);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_title:
                setToolbarTitle(false);
                farmingCapitalDialog();
                break;
        }
    }


    private void setToolbarTitle(boolean isDown) {
        Drawable drawable = getResources().getDrawable(isDown ? R.mipmap.ic_down : R.mipmap.ic_up);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mToolbarTitle.setCompoundDrawables(null, null, drawable, null);
    }


    private void farmingCapitalDialog() {
        if (mDialog == null) {
            mDialog = new FarmingCapitalSelectDialog(this, mToolbar, mFragmentChangeManager.getCurrentTab());
            mDialog.setOnDismissListener(dialog1 -> {
                setToolbarTitle(true);
            });
            mDialog.setOnTagClick(view -> {
                switch (view.getId()){
                    case R.id.tv_my_order:
                        mFragmentChangeManager.setFragments(0);
                        setTitle("我的订单");
                        break;
                    case R.id.tv_repayment_record:
                        mFragmentChangeManager.setFragments(1);
                        setTitle("还款记录");
                        break;
                    case R.id.tv_return:
                        mFragmentChangeManager.setFragments(2);
                        setTitle("退货/退款");
                        break;
                }
                mDialog.setMtab(mFragmentChangeManager.getCurrentTab());
                mDialog.dismiss();
            });
        }
        mDialog.show();
    }

}
