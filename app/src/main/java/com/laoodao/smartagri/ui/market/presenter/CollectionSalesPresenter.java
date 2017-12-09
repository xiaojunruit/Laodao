package com.laoodao.smartagri.ui.market.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.market.contact.CollectionSalesContact;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 */

public class CollectionSalesPresenter extends ListPresenter<CollectionSalesContact.CollectionSalesView> implements CollectionSalesContact.Presenter<CollectionSalesContact.CollectionSalesView> {
    ServiceManager mServiceManager;

    @Inject
    public CollectionSalesPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDate(int page, int type) {
        Observable<Page<Collection>> fromNetWork;
        Observable<Page<Collection>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.COLLECTION_SALES + page;
            fromNetWork = mServiceManager.getMarketService()
                    .getMyCollect(page, type).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.<Page<Collection>>rxCreateDiskObservable(key, new TypeToken<Page<Collection>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getMarketService()
                    .getMyCollect(page, type).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(new Subscriber<Page<Collection>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.onEmpty();
            }

            @Override
            public void onNext(Page<Collection> collectionPage) {
                mView.initData(collectionPage.data.items, collectionPage.data.page < 2);
                if (!NetworkUtils.isAvailable(LDApplication.getInstance()) && collectionPage.data.total == 0) {
                    mView.onError();
                } else if (collectionPage.data.total == 0) {
                    mView.onEmpty();
                } else {
                    mView.onContent();
                }
                boolean noMore = collectionPage.data.size * collectionPage.data.page >= collectionPage.data.total;
                mView.noMore(noMore);
            }
        });

    }
}
