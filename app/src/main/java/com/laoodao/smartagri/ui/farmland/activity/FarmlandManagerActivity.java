package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.ui.farmland.contract.FarmlandManagerContract;
import com.laoodao.smartagri.ui.farmland.fragment.FarmlandManagementFragment;
import com.laoodao.smartagri.ui.farmland.fragment.MyFarmlandFragment;
import com.laoodao.smartagri.ui.farmland.presenter.FarmlandManagerPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 农田管理
 */

public class FarmlandManagerActivity extends BaseActivity<FarmlandManagerPresenter> implements FarmlandManagerContract.FarmlandManagerView {
    @BindView(R.id.tv_farmland)
    TextView mTvFarmland;
    @BindView(R.id.tv_books)
    TextView mTvBooks;
    private int tab;
    private String title;
    private FragmentChangeManager mFragmentChangeManager;

    public static void start(Context context, int tab, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("tab", tab);
        bundle.putString("title", title);
        UiUtils.startActivity(context, FarmlandManagerActivity.class, bundle);
    }


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_farmland_manager;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        title = getIntent().getStringExtra("title");
        setTitle(title);
        tab = getIntent().getIntExtra("tab", 0);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyFarmlandFragment());
        fragments.add(new FarmlandManagementFragment());
        mFragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.content, fragments);
        if (tab == 0) {
            mTvFarmland.setSelected(true);
            mFragmentChangeManager.setFragments(0);
        } else {
            mTvBooks.setSelected(true);
            mFragmentChangeManager.setFragments(1);
        }
    }

    @OnClick({R.id.tv_farmland, R.id.tv_books})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_farmland:
                if (mTvFarmland.isSelected()) {
                    return;
                }
                mFragmentChangeManager.setFragments(0);
                mTvFarmland.setSelected(!mTvFarmland.isSelected());
                mTvBooks.setSelected(!mTvFarmland.isSelected());
                break;
            case R.id.tv_books:
                if (mTvBooks.isSelected()) {
                    return;
                }
                mFragmentChangeManager.setFragments(1);
                mTvBooks.setSelected(!mTvBooks.isSelected());
                mTvFarmland.setSelected(!mTvBooks.isSelected());
                break;
        }
    }
}
