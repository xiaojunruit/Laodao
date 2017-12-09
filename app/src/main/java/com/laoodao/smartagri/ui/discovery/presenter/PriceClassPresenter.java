package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.PriceClassContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class PriceClassPresenter extends ListPresenter<PriceClassContract.PriceClassView> implements PriceClassContract.Presenter<PriceClassContract.PriceClassView> {


    ServiceManager mServiceManager;

    @Inject
    public PriceClassPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestData(int cateId, int page, int timeType, String keyword, String pos,int areaId) {
        mServiceManager.getDiscoverService()
                .getPriceQuotation(cateId, page, timeType,keyword,pos,areaId)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}