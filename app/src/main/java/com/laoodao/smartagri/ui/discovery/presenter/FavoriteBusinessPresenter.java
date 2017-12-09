package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.FavoriteBusinessContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/7/22.
 */

public class FavoriteBusinessPresenter extends RxPresenter<FavoriteBusinessContract.FavoriteBusinessView> implements FavoriteBusinessContract.Presenter<FavoriteBusinessContract.FavoriteBusinessView> {
    ServiceManager mServiceManager;

    @Inject
    public FavoriteBusinessPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }



}
