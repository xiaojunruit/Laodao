package com.laoodao.smartagri.ui.user.presenter;

import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Pagination;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.IntegralDetailContract;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class IntegralDetailPresenter extends ListPresenter<IntegralDetailContract.IntegralDetailView> implements IntegralDetailContract.Presenter<IntegralDetailContract.IntegralDetailView> {

    ServiceManager mServiceManager;

    @Inject
    public IntegralDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestData(int page) {

        Observable<Page<IntegralDetail>> fromNetWork;
        Observable<Page<IntegralDetail>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.INTEGRAL_DETAILED + page;
            fromNetWork = mServiceManager.getUserService().getIntegralDetail(page).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.<Page<IntegralDetail>>rxCreateDiskObservable(key, new TypeToken<Page<IntegralDetail>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getUserService().getIntegralDetail(page).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);


//        mServiceManager.getUserService()
//                .getIntegralDetail(page)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);

    }
}
