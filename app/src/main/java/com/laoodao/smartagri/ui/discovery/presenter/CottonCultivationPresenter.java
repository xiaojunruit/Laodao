package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.ui.discovery.contract.CottonCultivationContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/6/30.
 */

public class CottonCultivationPresenter extends RxPresenter<CottonCultivationContract.CottonCultivationQueryView> implements CottonCultivationContract.Presenter<CottonCultivationContract.CottonCultivationQueryView> {

    ServiceManager mServiceManager;

    @Inject
    public CottonCultivationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


}
