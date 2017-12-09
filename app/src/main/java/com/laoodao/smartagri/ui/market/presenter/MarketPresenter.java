package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.market.contact.MarketContact;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/15.
 */

public class MarketPresenter extends RxPresenter<MarketContact.MarketView> implements MarketContact.Presenter<MarketContact.MarketView> {
    ServiceManager mServiceManager;

    @Inject
    public MarketPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
