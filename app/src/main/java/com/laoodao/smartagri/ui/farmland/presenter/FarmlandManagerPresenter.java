package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.farmland.contract.FarmlandManagerContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/23.
 */

public class FarmlandManagerPresenter extends RxPresenter<FarmlandManagerContract.FarmlandManagerView> implements FarmlandManagerContract.Presenter<FarmlandManagerContract.FarmlandManagerView> {
    ServiceManager mServiceManager;

    @Inject
    public FarmlandManagerPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
