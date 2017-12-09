package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.PesticideDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.PesticideDetailContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/17.
 */

public class PesticideDetailPresenter extends ListPresenter<PesticideDetailContract.PesticideDetailView> implements PesticideDetailContract.Presenter<PesticideDetailContract.PesticideDetailView> {


    ServiceManager mServiceManager;

    @Inject
    public PesticideDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDetail(int id) {
        mServiceManager.getDiscoverService()
                .infoPesticideDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<PesticideDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<PesticideDetail> result) {
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