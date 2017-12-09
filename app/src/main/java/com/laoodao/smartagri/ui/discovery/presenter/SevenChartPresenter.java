package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.SevenChartContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class SevenChartPresenter extends RxPresenter<SevenChartContract.SevenChartView> implements SevenChartContract.Presenter<SevenChartContract.SevenChartView> {


    ServiceManager mServiceManager;

    @Inject
    public SevenChartPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}