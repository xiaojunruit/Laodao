package com.laoodao.smartagri.base;

import android.text.TextUtils;
import android.util.Log;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.RxUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 欧源 on 2017/5/7.
 */

public class ListPresenter<T extends ListBaseView> extends RxPresenter<T> {


    public <T extends Response> Observable.Transformer<T, T> transform() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    LogUtil.e("===>>>throwable:" + throwable.toString());
                    mView.onError();
                    return Observable.empty();
                })
                .compose(RxUtils.bindToLifecycle(mView))
                .filter(result -> Api.doCheck(result))
                .doAfterTerminate(() -> mView.complete());
    }

    public <Item> void onPageLoaded(Page<Item> result) {
        mView.setResult(result.data.items, result.data.page < 2);
        if (!NetworkUtils.isAvailable(LDApplication.getInstance()) && result.data.total == 0) {
            mView.onError();
        } else if (result.data.total == 0) {
            mView.onEmpty();
        } else {
            mView.onContent();
        }
        boolean noMore = result.data.size * result.data.page >= result.data.total;
        mView.noMore(noMore);
    }


}
