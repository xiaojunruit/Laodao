package com.laoodao.smartagri.ui.qa.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyAnswer;
import com.laoodao.smartagri.bean.WonderAnswer;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.qa.contract.WonderAnswerContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/24.
 */

public class WonderAnswerPresenter extends ListPresenter<WonderAnswerContract.WonderAnswerView> implements WonderAnswerContract.Presenter<WonderAnswerContract.WonderAnswerView> {
    ServiceManager mServiceManager;

    @Inject
    public WonderAnswerPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getWonderAnswerList(int page) {
        Observable<Page<WonderAnswer>> fromNetWork;
        Observable<Page<WonderAnswer>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.WONDER_ANSWER_LIST_ + page;
            fromNetWork = mServiceManager.getQAService().wonderList(page).compose(RxUtils.<Page<WonderAnswer>>rxCacheListHelper(key));
            observable = RxUtils.<Page<WonderAnswer>>rxCreateDiskObservable(key, new TypeToken<Page<WonderAnswer>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getQAService().wonderList(page).compose(transform());
        }

        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);

//        mServiceManager.getQAService()
//                .wonderList(page)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
    }
}
