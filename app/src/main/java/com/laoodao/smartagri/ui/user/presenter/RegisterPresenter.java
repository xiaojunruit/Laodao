package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.RegisterContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class RegisterPresenter extends RxPresenter<RegisterContract.RegisterView> implements RegisterContract.Presenter<RegisterContract.RegisterView> {
    ServiceManager mServiceManager;

    @Inject
    public RegisterPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getCode(String phone, String rule) {
        mServiceManager.getUserService()
                .sendCode(phone, rule)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.countdown();
                    }
                });
    }

    @Override
    public void register(String username, String pwd, String code) {
        mServiceManager.getUserService()
                .register(username, pwd, code)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<User> result) {
                        mView.registerSuccess(result.data);
                    }
                });
    }
}
