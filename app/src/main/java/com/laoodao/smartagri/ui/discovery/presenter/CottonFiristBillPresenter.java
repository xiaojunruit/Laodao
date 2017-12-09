package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.CottonFiristBillContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class CottonFiristBillPresenter extends RxPresenter<CottonFiristBillContract.CottonFiristBillView> implements CottonFiristBillContract.Presenter<CottonFiristBillContract.CottonFiristBillView> {
    ServiceManager mServiceManager;

    @Inject
    public CottonFiristBillPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
