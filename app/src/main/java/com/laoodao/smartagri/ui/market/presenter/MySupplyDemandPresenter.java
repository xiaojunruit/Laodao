package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.market.contact.MySupplyDemandContact;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/15.
 */

public class MySupplyDemandPresenter extends RxPresenter<MySupplyDemandContact.MySupplyDemandView> implements MySupplyDemandContact.Presenter<MySupplyDemandContact.MySupplyDemandView> {
    ServiceManager mServiceManager;

    @Inject
    public MySupplyDemandPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}
