package com.laoodao.smartagri.base;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface BasePresenter<T> {

    void attachView(T view);

    void detachView();


}
