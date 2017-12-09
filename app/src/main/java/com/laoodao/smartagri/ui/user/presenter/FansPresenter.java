package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.user.contract.FansContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class FansPresenter extends ListPresenter<FansContract.FansView> implements FansContract.Presenter<FansContract.FansView> {


    ServiceManager mServiceManager;

    @Inject
    public FansPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList(int memberId, int page, int type) {
        mServiceManager.getUserService()
                .wonderUser(memberId, page, type)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }

    @Override
    public void Follow(int id) {
        mServiceManager.getUserService()
                .addWonfer(id)
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
}