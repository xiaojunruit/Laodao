package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.NewsContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/4/21.
 */

public class NewsPresenter extends ListPresenter<NewsContract.NewsView> implements NewsContract.Presenter<NewsContract.NewsView> {

    ServiceManager mServiceManager;

    @Inject
    public NewsPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestNewMenu() {
        mServiceManager.getDiscoverService()
                .getNewsMenu()
                .compose(transform())
                .subscribe(new Subscriber<Page<MechanicalType>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Page<MechanicalType> listResult) {
                        mView.onContent();
                        mView.newMenuSuccess(listResult.data.items);
                    }
                });
    }
}
