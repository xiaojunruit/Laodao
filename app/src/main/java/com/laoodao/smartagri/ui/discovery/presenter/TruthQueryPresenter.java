package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.TruthQueryContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TruthQueryPresenter extends RxPresenter<TruthQueryContract.TruthQueryView> implements TruthQueryContract.Presenter<TruthQueryContract.TruthQueryView> {
    ServiceManager mServiceManager;

    @Inject
    public TruthQueryPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
