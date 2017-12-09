package com.laoodao.smartagri.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.di.component.AppComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 欧源 on 2017/4/12.
 */

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment {


    protected View mRootView;
    private Unbinder mUnbinder;
    protected FragmentActivity mActivity;
    protected LayoutInflater mInflater;
    protected LDApplication mApplication;

    @Inject
    protected P mPresenter;


    @LayoutRes
    public abstract int getLayoutResId();


    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        this.mInflater = inflater;
        mUnbinder = ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (FragmentActivity) getActivity();
        mApplication = (LDApplication) mActivity.getApplication();
        setupActivityComponent(mApplication.getAppComponent());
        attachView();
        configViews();
        if (enableEventBus())
            EventBus.getDefault().register(this);
    }

    protected final void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (enableEventBus())
            EventBus.getDefault().unregister(this);
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
    }

    protected boolean enableEventBus() {
        return false;
    }

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();


    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}
