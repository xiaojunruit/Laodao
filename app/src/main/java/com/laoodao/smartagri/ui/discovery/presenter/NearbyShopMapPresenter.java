package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.NearbyShopMapContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/8/9.
 */

public class NearbyShopMapPresenter extends RxPresenter<NearbyShopMapContract.NearbyShopMapView> implements NearbyShopMapContract.Presenter<NearbyShopMapContract.NearbyShopMapView> {
    ServiceManager mServiceManager;

    @Inject
    public NearbyShopMapPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
