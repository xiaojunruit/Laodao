package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.TruthQuerySearchContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/5/3.
 */

public class TruthQuerySearchPresenter extends ListPresenter<TruthQuerySearchContract.TruthQuerySearchView> implements TruthQuerySearchContract.Presenter<TruthQuerySearchContract.TruthQuerySearchView> {
    ServiceManager mServiceManager;

    @Inject
    public TruthQuerySearchPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestSearch(int page, String tag) {
        mServiceManager.getDiscoverService()
                .infoSearch(page, tag)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}
