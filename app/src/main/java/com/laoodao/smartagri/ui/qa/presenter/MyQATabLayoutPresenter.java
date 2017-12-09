package com.laoodao.smartagri.ui.qa.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.qa.contract.MyQATabLayoutContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyQATabLayoutPresenter extends RxPresenter<MyQATabLayoutContract.MyQAView> implements MyQATabLayoutContract.Presenter<MyQATabLayoutContract.MyQAView> {
    ServiceManager mServiceManager;

    @Inject
    public MyQATabLayoutPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
