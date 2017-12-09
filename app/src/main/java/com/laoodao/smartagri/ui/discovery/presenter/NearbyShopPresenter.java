package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.discovery.contract.NearbyShopContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/3.
 */

public class NearbyShopPresenter extends ListPresenter<NearbyShopContract.NearbyShopView> implements NearbyShopContract.Presenter<NearbyShopContract.NearbyShopView> {
    ServiceManager mServiceManager;

    @Inject
    public NearbyShopPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestNearbyShop(int page, String lat, String lng) {
        mServiceManager.getDiscoverService()
//                .nearbyShop(page, "38.858679", "105.710368")
                .nearbyShop(page, lat, lng)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}
