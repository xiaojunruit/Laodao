package com.laoodao.smartagri.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.RxFragment;

/**
 * Created by 欧源 on 2017/5/6.
 */

public abstract class LazyFragment<P extends BasePresenter> extends BaseFragment<P> {

    protected boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    protected boolean hasFetchData; // 标识已经触发过懒加载数据


    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected abstract void lazyFetchData();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }


    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            lazyFetchDataIfPrepared();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hasFetchData = false;
        isViewPrepared = false;
    }
}
