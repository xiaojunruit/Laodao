package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.MyOrderListContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/2.
 */

public class MyOrderListPresenter extends ListPresenter<MyOrderListContract.MyOrderListView> implements MyOrderListContract.Presenter<MyOrderListContract.MyOrderListView> {
    ServiceManager mServiceManager;

    @Inject
    public MyOrderListPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestMyOrder(int page,int type) {
        mServiceManager.getUserService()
                .getMyOrder(page,type)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }

}
