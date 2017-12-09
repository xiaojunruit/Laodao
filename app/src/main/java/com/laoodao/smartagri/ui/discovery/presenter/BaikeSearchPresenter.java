package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.discovery.contract.BaikeSearchContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/20.
 */

public class BaikeSearchPresenter extends ListPresenter<BaikeSearchContract.BaikeSearchView> implements BaikeSearchContract.Presenter<BaikeSearchContract.BaikeSearchView> {


    ServiceManager mServiceManager;

    @Inject
    public BaikeSearchPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList(int page, String kw) {
        mServiceManager.getDiscoverService()
                .baikeIndex(page, kw)
                .compose(transform())
                .subscribe(new Subscriber<Page<Baike>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<Baike> baikePage) {
                        onPageLoaded(baikePage);
                        mView.showList();
                    }
                });
    }
}