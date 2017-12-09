package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.farmland.contract.RecordContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/23.
 */

public class RecordPresenter extends RxPresenter<RecordContract.FarmIncomeView> implements RecordContract.Presenter<RecordContract.FarmIncomeView> {
    ServiceManager mServiceManager;

    @Inject
    public RecordPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
