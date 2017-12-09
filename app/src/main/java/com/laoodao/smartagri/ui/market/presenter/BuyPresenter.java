package com.laoodao.smartagri.ui.market.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.market.contact.BuyContact;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/15.
 */

public class BuyPresenter extends ListPresenter<BuyContact.BuyView> implements BuyContact.Presenter<BuyContact.BuyView> {
    ServiceManager mServiceManager;

    @Inject
    public BuyPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestBuy(int page, int type, String keyword, String category, String areaId, String timeId) {

        Observable<Page<Supplylists>> fromNetWork;
        Observable<Page<Supplylists>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.BUY_LIST + page;
            fromNetWork = mServiceManager.getMarketService().getSupplylists(page, type, keyword, category, areaId, timeId).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.rxCreateDiskObservable(key, new TypeToken<Page<Supplylists>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getMarketService().getSupplylists(page, type, keyword, category, areaId, timeId).compose(transform());
        }
        Observable.concat(observable, fromNetWork)
                .compose(transform())
                .subscribe(this::onPageLoaded);
//        mServiceManager.getMarketService()
//                .getSupplylists(page, type, keyword, category, areaId, timeId)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);

    }

    @Override
    public void requestDateType() {
        Observable<Result<List<SupplyMenu>>> fromNetWork = mServiceManager.getMarketService().supplyDate().compose(RxUtils.rxCacheListHelper(Constant.BUY_DATE_TYPE));
        Observable<Result<List<SupplyMenu>>> observable = RxUtils.<Result<List<SupplyMenu>>>rxCreateDiskObservable(Constant.BUY_DATE_TYPE, new TypeToken<Result<List<SupplyMenu>>>() {
        }.getType());
        Observable.concat(observable, fromNetWork)
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
        Observable<Result<List<SupplyMenu>>> fromNetWork = mServiceManager.getMarketService().getMenu(0).compose(RxUtils.rxCacheListHelper(Constant.BUY_MENU_TYPE));
        Observable<Result<List<SupplyMenu>>> observable = RxUtils.rxCreateDiskObservable(Constant.BUY_MENU_TYPE, new TypeToken<Result<List<SupplyMenu>>>() {
        }.getType());
        Observable.concat(observable, fromNetWork)
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
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Result<List<SupplyMenu>> listResult) {
//                        mView.getCategoryMenuList(listResult.data);
//                    }
//                });
    }

}
