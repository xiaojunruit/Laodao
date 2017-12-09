package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.ConcernCropPriceContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class ConcernCropPricePresenter extends ListPresenter<ConcernCropPriceContract.ConcernCropPriceView> implements ConcernCropPriceContract.Presenter<ConcernCropPriceContract.ConcernCropPriceView> {


    ServiceManager mServiceManager;

    @Inject
    public ConcernCropPricePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList() {
        mServiceManager.getDiscoverService()
                .recommendWonder()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<PriceWonder>>>() {
                    @Override
                    public void onCompleted() {
                       mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<List<PriceWonder>> listResult) {
                        if (listResult.data == null) {
                            mView.onEmpty();
                        } else {
                            mView.SuccessList(listResult.data);
                        }

                    }
                });
    }

    @Override
    public void requestAdd(int id) {
        mServiceManager.getDiscoverService()
                .addCottonFieldWonder(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        mView.successAdd();
                    }
                });
    }
}