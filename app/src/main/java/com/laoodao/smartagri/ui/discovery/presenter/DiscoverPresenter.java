package com.laoodao.smartagri.ui.discovery.presenter;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Discover;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.SupplyMarketing;
import com.laoodao.smartagri.bean.WonderAnswer;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.discovery.contract.DiscoverContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;
import com.laoodao.smartagri.view.wheelPicker.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class DiscoverPresenter extends ListPresenter<DiscoverContract.FindView> implements DiscoverContract.Presenter<DiscoverContract.FindView> {
    ServiceManager mServiceManager;

    @Inject
    public DiscoverPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestDiscover(int page, String lat, String lng) {
        Observable<Page<NearbyShop>> fromNetWork;
        Observable<Page<NearbyShop>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.DISCOVER_LIST_ + page;
            fromNetWork = mServiceManager.getDiscoverService().nearbyShop(page, lat, lng).compose(RxUtils.<Page<NearbyShop>>rxCacheListHelper(key));
            observable = RxUtils.<Page<NearbyShop>>rxCreateDiskObservable(key, new TypeToken<Page<NearbyShop>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getDiscoverService().nearbyShop(page, lat, lng).compose(transform());
        }

        Observable.concat(observable, fromNetWork).
                compose(transform())
                .doAfterTerminate(() -> mView.complete())
                .subscribe(new Subscriber<Page<NearbyShop>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Page<NearbyShop> nearbyShopPage) {
                        boolean noMore = nearbyShopPage.data.page * nearbyShopPage.data.size >= nearbyShopPage.data.total;
                        mView.noMore(noMore);
                        mView.discoverSuccess(nearbyShopPage.data.items, nearbyShopPage.data.page < 2);
                    }
                });

//        mServiceManager.getDiscoverService()
//                .nearbyShop(page, lat, lng)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
    }

    @Override
    public void requestMeun(int type) {
        String key = Constant.DISCOVER_MENU;
        Observable<Result<List<Discovercat>>> fromNetWork = mServiceManager.getDiscoverService().discoverMenu(type).compose(RxUtils.rxCacheListHelper(key));
        Observable.concat(RxUtils.<Result<List<Discovercat>>>rxCreateDiskObservable(key, new TypeToken<Result<List<Discovercat>>>() {
        }.getType()), fromNetWork)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Discovercat>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<List<Discovercat>> data) {
                        mView.menuSucess(data.data);
                    }
                });


    }
}
