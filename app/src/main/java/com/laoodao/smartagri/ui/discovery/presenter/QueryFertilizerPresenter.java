package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.ui.discovery.contract.QueryFertilizerContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/5/16.
 */

public class QueryFertilizerPresenter extends ListPresenter<QueryFertilizerContract.QueryResultView> implements QueryFertilizerContract.Presenter<QueryFertilizerContract.QueryResultView> {
    ServiceManager mServiceManager;

    @Inject
    public QueryFertilizerPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestFertilizer(int page, String number, String commonName, String company) {
        mServiceManager.getDiscoverService()
                .infoFertilizer(page, number, commonName, company)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}
