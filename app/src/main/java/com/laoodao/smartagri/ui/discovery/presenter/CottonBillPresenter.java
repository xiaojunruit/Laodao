package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.CottonBillContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/7/20.
 */

public class CottonBillPresenter extends RxPresenter<CottonBillContract.CottonBillView> implements CottonBillContract.Presenter<CottonBillContract.CottonBillView> {
    ServiceManager mServiceManager;

    @Inject
    public CottonBillPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}
