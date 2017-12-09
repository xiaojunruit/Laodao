package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.farmland.contract.ImagePreviewContract;

import javax.inject.Inject;


/**
 * Created by 欧源 on 2017/4/24.
 */

public class ImagePreviewPresenter extends RxPresenter<ImagePreviewContract.ImagePreviewView> implements ImagePreviewContract.Presenter<ImagePreviewContract.ImagePreviewView> {


    ServiceManager mServiceManager;

    @Inject
    public ImagePreviewPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}
