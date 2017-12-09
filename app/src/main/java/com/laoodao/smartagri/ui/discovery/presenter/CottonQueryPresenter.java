package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.CottonQueryContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/6/28.
 */

public class CottonQueryPresenter extends RxPresenter<CottonQueryContract.CottonQueryView> implements CottonQueryContract.Presenter<CottonQueryContract.CottonQueryView> {


    ServiceManager mServiceManager;

    @Inject
    public CottonQueryPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}