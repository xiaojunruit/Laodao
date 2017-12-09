package com.laoodao.smartagri.ui.qa.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.qa.contract.QAContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/15.
 */

public class QAPresenter extends RxPresenter<QAContract.QAView> implements QAContract.Presenter<QAContract.QAView> {

    ServiceManager mServiceManager;

    @Inject
    public QAPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}
