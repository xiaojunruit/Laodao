package com.laoodao.smartagri.ui.qa.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyAnswer;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.qa.contract.MyAnswerContract;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyAnswerPresenter extends ListPresenter<MyAnswerContract.MyAnswerQAView> implements MyAnswerContract.Presenter<MyAnswerContract.MyAnswerQAView> {
    ServiceManager mServiceManager;

    @Inject
    public MyAnswerPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getMyAnswerList(int page) {
        Observable<Page<MyAnswer>> fromNetWork;
        Observable<Page<MyAnswer>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.MY_ANSWER_LIST_ + page;
            fromNetWork = mServiceManager.getQAService().showMyAnswerList(page).compose(RxUtils.<Page<MyAnswer>>rxCacheListHelper(key));
            observable = RxUtils.<Page<MyAnswer>>rxCreateDiskObservable(key, new TypeToken<Page<MyAnswer>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getQAService().showMyAnswerList(page).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);

//        mServiceManager.getQAService()
//                .showMyAnswerList(page)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
    }
}
