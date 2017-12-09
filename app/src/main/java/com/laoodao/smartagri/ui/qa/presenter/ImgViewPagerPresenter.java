package com.laoodao.smartagri.ui.qa.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.qa.contract.ImgViewPagerContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/19.
 */

public class ImgViewPagerPresenter extends RxPresenter<ImgViewPagerContract.ImgViewPagerContactView> implements ImgViewPagerContract.Presenter<ImgViewPagerContract.ImgViewPagerContactView> {
    ServiceManager mServiceManager;

    @Inject
    public ImgViewPagerPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
