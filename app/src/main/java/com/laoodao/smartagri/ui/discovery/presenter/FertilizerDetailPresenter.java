package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.FertilizerDetail;
import com.laoodao.smartagri.bean.MicrobialFertilizerDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.FertilizerDetailContract;
import com.laoodao.smartagri.ui.discovery.contract.MicrobialFertilizerDetailContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/16.
 */

public class FertilizerDetailPresenter extends ListPresenter<FertilizerDetailContract.FertilizerDetailView> implements FertilizerDetailContract.Presenter<FertilizerDetailContract.FertilizerDetailView> {


    ServiceManager mServiceManager;

    @Inject
    public FertilizerDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDetail(int id) {
        mServiceManager.getDiscoverService()
                .infoFertilizerDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<FertilizerDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<FertilizerDetail> data) {
                        if (data.data == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.getDetail(data.data);

                    }
                });
    }
}