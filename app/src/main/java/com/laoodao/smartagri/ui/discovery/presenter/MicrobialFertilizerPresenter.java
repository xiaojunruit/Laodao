package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.MicrobialFertilizerContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/5/16.
 */

public class MicrobialFertilizerPresenter extends ListPresenter<MicrobialFertilizerContract.MicrobialFertilizerView> implements MicrobialFertilizerContract.Presenter<MicrobialFertilizerContract.MicrobialFertilizerView> {


    ServiceManager mServiceManager;

    @Inject
    public MicrobialFertilizerPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestMicrobialFertilizer(int page, String number, String commonName, String company) {
        mServiceManager.getDiscoverService()
                .infoMicrobialFertilizer(page, number, commonName, company)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}