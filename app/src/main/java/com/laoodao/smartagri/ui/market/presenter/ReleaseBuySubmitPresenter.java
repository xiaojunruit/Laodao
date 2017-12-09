package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.market.contact.ReleaseBuySubmitContact;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ReleaseBuySubmitPresenter extends RxPresenter<ReleaseBuySubmitContact.ReleaseBuySubmitView> implements ReleaseBuySubmitContact.Presenter<ReleaseBuySubmitContact.ReleaseBuySubmitView> {
    ServiceManager mServiceManager;

    @Inject
    public ReleaseBuySubmitPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
