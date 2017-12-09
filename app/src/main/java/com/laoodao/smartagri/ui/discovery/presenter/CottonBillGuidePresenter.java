package com.laoodao.smartagri.ui.discovery.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.discovery.contract.CottonBillGuideContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/7/20.
 */

public class CottonBillGuidePresenter extends RxPresenter<CottonBillGuideContract.CottonBillGuideView> implements CottonBillGuideContract.Presenter<CottonBillGuideContract.CottonBillGuideView> {

    ServiceManager mServiceManager;

    @Inject
    public CottonBillGuidePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


//    @Override
//    public void cottonIndex() {
//        Observable<Result<String>> fromNetWork;
//        Observable<Result<String>> observable = Observable.empty();
//        String key = Constant.COTTON_BILL_IMG;
//        fromNetWork = mServiceManager.getDiscoverService().cottonIndex().compose(RxUtils.rxCacheListHelper(key));
//        observable = RxUtils.<Result<String>>rxCreateDiskObservable(key, new TypeToken<Result<String>>() {
//        }.getType());
//        Observable.concat(observable, fromNetWork).compose(Api.checkOn(mView)).subscribe(new Subscriber<Result<String>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onNext(Result<String> stringResult) {
//                mView.setImage(stringResult.data);
//            }
//        });
//
//    }
}
