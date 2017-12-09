package com.laoodao.smartagri.ui.qa.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Ask;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.qa.contract.MyAskContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyAskPresenter extends ListPresenter<MyAskContract.MyQAView> implements MyAskContract.Presenter<MyAskContract.MyQAView> {
    ServiceManager mServiceManager;

    @Inject
    public MyAskPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void getMyAskList(int page) {
        Observable<Page<Ask>> fromNetWork;
        Observable<Page<Ask>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.NEW_MY_ASK_ + page;
            fromNetWork = mServiceManager.getQAService().getMyAskList(page).compose(RxUtils.<Page<Ask>>rxCacheListHelper(key));
            observable = RxUtils.<Page<Ask>>rxCreateDiskObservable(key, new TypeToken<Page<Ask>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getQAService().getMyAskList(page).compose(transform());
        }

        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);

//        mServiceManager
//                .getQAService()
//                .getMyAskList(page)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
    }
}
