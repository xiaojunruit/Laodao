package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.AccountDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.farmland.contract.AccountDetailContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AccountDetailPresenter extends RxPresenter<AccountDetailContract.AccountDetailView> implements AccountDetailContract.Presenter<AccountDetailContract.AccountDetailView> {

    ServiceManager mServiceManager;

    @Inject
    public AccountDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestAccountDetail(int id) {
        mServiceManager.getFarmlandService()
                .getAccountDetail(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<AccountDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<AccountDetail> result) {
                        mView.initAccountDetail(result.data);
                    }
                });

    }

    @Override
    public void requestResult(int id) {
        mServiceManager.getFarmlandService()
                .deleteAccount(id)
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
                        mView.getResult(response);
                    }
                });
    }
}
