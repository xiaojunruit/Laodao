package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.market.contact.MyBrowseContact;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyBrowsePresenter extends RxPresenter<MyBrowseContact.MyBrowseView> implements MyBrowseContact.Presenter<MyBrowseContact.MyBrowseView> {
    ServiceManager mServiceManager;

    @Inject
    public MyBrowsePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
