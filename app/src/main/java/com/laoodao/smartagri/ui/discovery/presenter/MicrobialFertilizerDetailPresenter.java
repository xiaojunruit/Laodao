package com.laoodao.smartagri.ui.discovery.presenter;

import android.text.TextUtils;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MicrobialFertilizerDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.MicrobialFertilizerDetailContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/16.
 */

public class MicrobialFertilizerDetailPresenter extends ListPresenter<MicrobialFertilizerDetailContract.MicrobialFertilizerDetailView> implements MicrobialFertilizerDetailContract.Presenter<MicrobialFertilizerDetailContract.MicrobialFertilizerDetailView> {


    ServiceManager mServiceManager;

    @Inject
    public MicrobialFertilizerDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDetail(int id) {
        mServiceManager.getDiscoverService()
                .getMicrobialFertilizerDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<MicrobialFertilizerDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<MicrobialFertilizerDetail> data) {
                        if (data.data == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.getMicrobialFertilizerDetail(data.data);

                    }
                });
    }
}