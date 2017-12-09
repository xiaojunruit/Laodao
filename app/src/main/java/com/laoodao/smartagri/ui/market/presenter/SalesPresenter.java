package com.laoodao.smartagri.ui.market.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyArea;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.market.contact.SalesContact;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/15.
 */

public class SalesPresenter extends ListPresenter<SalesContact.SalesView> implements SalesContact.Presenter<SalesContact.SalesView> {
    ServiceManager mServiceManager;

    @Inject
    public SalesPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestSales(int page, int type, String keyword, String categoryId, String areaId, String timeId) {
//        mServiceManager.getMarketService()
//                .getSupplylists(page, type, keyword, categoryId, areaId, timeId)
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Page<Supplylists>>() {
//                    @Override
//                    public void onCompleted() {
//                        mView.complete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Page<Supplylists> supplylistsPage) {
//                        mView.initSales(supplylistsPage.data.items, supplylistsPage.data.page < 2);
//                        if (supplylistsPage.data.page * supplylistsPage.data.size >= supplylistsPage.data.total) {
//                            mView.noMore();
//                        }
//                    }
//                });

        Observable<Page<Supplylists>> fromNetWork;
        Observable<Page<Supplylists>> observable = Observable.empty();
        if (page == 1) {
            String key=Constant.SALES_LIST+page;
            fromNetWork = mServiceManager.getMarketService().getSupplylists(page, type, keyword, categoryId, areaId, timeId).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.rxCreateDiskObservable(key, new TypeToken<Page<Supplylists>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getMarketService().getSupplylists(page, type, keyword, categoryId, areaId, timeId).compose(transform());
        }
        Observable.concat(observable, fromNetWork)
                .compose(transform())
                .subscribe(this::onPageLoaded);
//        mServiceManager.getMarketService()
//                .getSupplylists(page, type, keyword, categoryId, areaId, timeId)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
    }

    @Override
    public void requestDateType() {

        Observable<Result<List<SupplyMenu>>> fromNetWork = mServiceManager.getMarketService()
                .supplyDate()
                .compose(RxUtils.rxCacheListHelper(Constant.SALES_DATA_TYPE));
        Observable<Result<List<SupplyMenu>>> observable = RxUtils.rxCreateDiskObservable(Constant.SALES_DATA_TYPE, new TypeToken<Result<List<SupplyMenu>>>() {
        }.getType());
        Observable.concat(observable,fromNetWork)
                .compose(transform())
                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<SupplyMenu>> listResult) {
                        mView.getDateMenuList(listResult.data);
                    }
                });
//        mServiceManager.getMarketService()
//                .supplyDate()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("onError->" + e.getMessage());
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Result<List<SupplyMenu>> selectionItemResult) {
//                        mView.getDateMenuList(selectionItemResult.data);
//                    }
//                });
    }

    @Override
    public void requestMenuType() {
        Observable<Result<List<SupplyMenu>>> fromNetWork = mServiceManager.getMarketService()
                .getMenu(0)
                .compose(RxUtils.rxCacheListHelper(Constant.SALES_MENU_TYPE));
        Observable<Result<List<SupplyMenu>>> observable = RxUtils.rxCreateDiskObservable(Constant.SALES_MENU_TYPE, new TypeToken<Result<List<SupplyMenu>>>() {
        }.getType());
        Observable.concat(observable,fromNetWork)
                .compose(transform())
                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<SupplyMenu>> listResult) {
                        mView.getCategoryMenuList(listResult.data);
                    }
                });
//        mServiceManager.getMarketService()
//                .getMenu(0)
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Result<List<SupplyMenu>> listResult) {
//                        mView.getCategoryMenuList(listResult.data);
//                    }
//                });
    }

}
