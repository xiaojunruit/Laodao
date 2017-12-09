package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.MyAttentionPriceContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class MyAttentionPricePresenter extends ListPresenter<MyAttentionPriceContract.MyAttentionPriceView> implements MyAttentionPriceContract.Presenter<MyAttentionPriceContract.MyAttentionPriceView> {


    ServiceManager mServiceManager;

    @Inject
    public MyAttentionPricePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList() {
        mServiceManager.getDiscoverService()
                .CottonMyWonder()
                .compose(transform())
                .subscribe(new Subscriber<Result<List<PriceWonder>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<List<PriceWonder>> listResult) {
                        mView.SuccessList(listResult.data);
                        if (listResult.data.size() == 0) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
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

                    }
                });
    }

    @Override
    public void requestWonderPrice(int id) {
        mServiceManager.getUserService()
                .wonderPrice(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<List<PriceWonder>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<List<PriceWonder>> listResult) {
                        mView.SuccessWonderPrice(listResult.data);
                        if (listResult.data.size() == 0) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                    }
                });
    }

}