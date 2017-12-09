package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.ui.discovery.contract.QuerySeedContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/5/16.
 */

public class QuerySeedPresenter extends ListPresenter<QuerySeedContract.QueryResultView> implements QuerySeedContract.Presenter<QuerySeedContract.QueryResultView> {
    ServiceManager mServiceManager;

    @Inject
    public QuerySeedPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestQuerySeed(int page, String number, String category, String variety) {
        mServiceManager.getDiscoverService()
                .infoSeed(page, number, category, variety)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}
