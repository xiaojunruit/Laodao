package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.user.contract.FarmingCapitalContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/28.
 */

public class FarmingCapitalPresenter extends RxPresenter<FarmingCapitalContract.FramingCapitalView> implements FarmingCapitalContract.Presenter<FarmingCapitalContract.FramingCapitalView> {
    ServiceManager mServiceManager;

    @Inject
    public FarmingCapitalPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
