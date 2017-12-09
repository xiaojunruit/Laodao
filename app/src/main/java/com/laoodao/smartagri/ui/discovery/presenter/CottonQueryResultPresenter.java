package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.CottonQueryResultContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/6/28.
 */

public class CottonQueryResultPresenter extends RxPresenter<CottonQueryResultContract.QueryResultView> implements CottonQueryResultContract.Presenter<CottonQueryResultContract.QueryResultView> {


    ServiceManager mServiceManager;

    @Inject
    public CottonQueryResultPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}