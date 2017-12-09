package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.ui.discovery.contract.NewsListContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by 欧源 on 2017/4/21.
 */

public class NewsListPresenter extends ListPresenter<NewsListContract.NewsListView> implements NewsListContract.Presenter<NewsListContract.NewsListView> {


    ServiceManager mServiceManager;

    @Inject
    public NewsListPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getNewsList(int page, int gcId, String kw) {
        mServiceManager.getDiscoverService()
                .getNewsList(page, gcId, kw)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}
