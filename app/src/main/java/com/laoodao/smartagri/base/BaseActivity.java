package com.laoodao.smartagri.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.ejz.multistateview.MultiStateView;
import com.hyphenate.chat.EMClient;
import com.jaeger.library.StatusBarUtil;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.di.component.AppComponent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/11.
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {

    protected LDApplication mApplication;

    @Inject
    protected P mPresenter;

    private Unbinder mUnbinder;
    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    private ActionBar mActionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (LDApplication) getApplication();
        setContentView(getLayoutId());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        setupActivityComponent(mApplication.getAppComponent());
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorAccent), 0);
        mPresenter.attachView(this);
        setupToolbar();
        configViews();

        if (enableEventBus())
            EventBus.getDefault().register(this);
    }

    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mToolbarTitle = ButterKnife.findById(this, R.id.tv_toolbar_title);
        if (mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(false);
        mToolbar.setContentInsetsAbsolute(0, 0);
    }


    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        if (toolbar != null) {
            if (mToolbarTitle != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    protected boolean enableEventBus() {
        return false;
    }

    /**
     * 隐藏
     *
     * @param views
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 显示
     *
     * @param views
     */
    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();//释放资源
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (enableEventBus())
            EventBus.getDefault().unregister(this);
        this.mUnbinder = null;
        this.mApplication = null;
        this.mPresenter = null;
    }


    protected abstract int getLayoutId();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected abstract void configViews();


}
