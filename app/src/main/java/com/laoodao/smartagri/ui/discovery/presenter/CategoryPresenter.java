package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.CategoryContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/5/18.
 */

public class CategoryPresenter extends RxPresenter<CategoryContract.CategoryView> implements CategoryContract.Presenter<CategoryContract.CategoryView> {


    ServiceManager mServiceManager;

    @Inject
    public CategoryPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

}