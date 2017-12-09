package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.ui.user.contract.MyOrderContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/28.
 */

public class MyOrderPresenter extends RxPresenter<MyOrderContract.MyOrderView> implements MyOrderContract.Presenter<MyOrderContract.MyOrderView> {
    ServiceManager mServiceManager;

    @Inject
    public MyOrderPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
