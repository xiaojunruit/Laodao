package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.user.contract.MyCollectionContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class MyCollectionPresenter extends RxPresenter<MyCollectionContract.MyCollectionView> implements MyCollectionContract.Presenter<MyCollectionContract.MyCollectionView> {


    ServiceManager mServiceManager;

    @Inject
    public MyCollectionPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}