package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.PesticideContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/5/16.
 */

public class PesticidePresenter extends ListPresenter<PesticideContract.PesticideView> implements PesticideContract.Presenter<PesticideContract.PesticideView> {


    ServiceManager mServiceManager;

    @Inject
    public PesticidePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestPesticide(int page, String number, String activePrinciple, String dose, String manufacturer) {
        mServiceManager.getDiscoverService()
                .infoPesticide(page, number, activePrinciple, dose, manufacturer)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}