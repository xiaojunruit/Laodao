package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.SeedDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.SeedDetailContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/17.
 */

public class SeedDetailPresenter extends ListPresenter<SeedDetailContract.SeedDetailView> implements SeedDetailContract.Presenter<SeedDetailContract.SeedDetailView> {


    ServiceManager mServiceManager;

    @Inject
    public SeedDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDetail(int id) {
        mServiceManager.getDiscoverService()
                .infoSeeddetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<SeedDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<SeedDetail> result) {
                        if (result == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.showDetail(result.data);
                    }
                });
    }
}