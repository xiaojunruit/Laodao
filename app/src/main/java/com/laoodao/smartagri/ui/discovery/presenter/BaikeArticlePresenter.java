package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.discovery.contract.BaikeArticleContract;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/5/18.
 */

public class BaikeArticlePresenter extends ListPresenter<BaikeArticleContract.BaikeArticleView> implements BaikeArticleContract.Presenter<BaikeArticleContract.BaikeArticleView> {


    ServiceManager mServiceManager;

    @Inject
    public BaikeArticlePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestBaike(int page, int cropId, int categoryId) {
        mServiceManager.getDiscoverService()
                .baikeIndex(page, cropId, categoryId)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}