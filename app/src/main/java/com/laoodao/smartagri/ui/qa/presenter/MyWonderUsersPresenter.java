package com.laoodao.smartagri.ui.qa.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.qa.contract.MyWonderUsersContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class MyWonderUsersPresenter extends ListPresenter<MyWonderUsersContract.MyWonderUsersView> implements MyWonderUsersContract.Presenter<MyWonderUsersContract.MyWonderUsersView> {


    ServiceManager mServiceManager;

    @Inject
    public MyWonderUsersPresenter(ServiceManager serviceManager) {
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
    public void Follow(int memberId) {
        mServiceManager.getUserService()
                .addWonfer(memberId)
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