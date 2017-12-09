package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.ValidatePhoneContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/21.
 */

public class ValidatePhonePresenter extends RxPresenter<ValidatePhoneContract.ValidatePhoneView> implements ValidatePhoneContract.Presenter<ValidatePhoneContract.ValidatePhoneView> {
    ServiceManager mServiceManager;

    @Inject
    public ValidatePhonePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void validateCode(String mobile, String code) {
        mServiceManager.getUserService()
                .validateCode(mobile, code)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String,String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->"+e.getMessage());
                    }

                    @Override
                    public void onNext(Result<Map<String,String>> result) {
                        mView.setToken(result.data.get("token"));
                    }
                });
    }

    @Override
    public void getCode(String mobile, String rule) {
        mServiceManager.getUserService()
                .sendCode(mobile, rule)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->"+e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.countdown();
                    }
                });
    }
}
