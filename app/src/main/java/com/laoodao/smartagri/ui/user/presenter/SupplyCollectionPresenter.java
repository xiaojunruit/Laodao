package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.ui.user.contract.SupplyCollectionContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class SupplyCollectionPresenter extends ListPresenter<SupplyCollectionContract.BuyView> implements SupplyCollectionContract.Presenter<SupplyCollectionContract.BuyView> {


    ServiceManager mServiceManager;

    @Inject
    public SupplyCollectionPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList(int memberId, int type, int page) {
        mServiceManager.getUserService()
                .sdCollect(memberId, type, page)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}