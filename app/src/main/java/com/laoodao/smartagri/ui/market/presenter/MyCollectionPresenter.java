package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.market.contact.MyCollectionContact;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyCollectionPresenter extends RxPresenter<MyCollectionContact.MyCollectionView> implements MyCollectionContact.Presenter<MyCollectionContact.MyCollectionView> {
    ServiceManager mServiceManager;

    @Inject
    public MyCollectionPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
