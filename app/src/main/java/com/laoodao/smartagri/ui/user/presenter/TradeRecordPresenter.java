package com.laoodao.smartagri.ui.user.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.TradeRecordContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/18.
 */

public class TradeRecordPresenter extends ListPresenter<TradeRecordContract.TradeRecordView> implements TradeRecordContract.Presenter<TradeRecordContract.TradeRecordView> {

    ServiceManager mServiceManager;

    @Inject
    public TradeRecordPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestTransactionRecord(int page) {
        Observable<Page<TradeRecord>> fromNetWork;
        Observable<Page<TradeRecord>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.TRANSACTION_RECORD + page;
            fromNetWork = mServiceManager.getUserService().transactionRecordList(page).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.<Page<TradeRecord>>rxCreateDiskObservable(key, new TypeToken<Page<TradeRecord>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getUserService().transactionRecordList(page).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);

//        mServiceManager.getUserService()
//                .transactionRecordList(page)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
    }


}
