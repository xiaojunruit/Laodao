package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.CottonMaxBillContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class CottonMaxBillPresenter extends RxPresenter<CottonMaxBillContract.CottonMaxBillView> implements CottonMaxBillContract.Presenter<CottonMaxBillContract.CottonMaxBillView> {
    ServiceManager mServiceManager;

    @Inject
    public CottonMaxBillPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
