package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.UpdatePhoneContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 * 绑定手机号码Presenter
 */

public class UpdatePhonePresenter extends RxPresenter<UpdatePhoneContract.UpdatePhoneView> implements UpdatePhoneContract.Presenter<UpdatePhoneContract.UpdatePhoneView> {
    ServiceManager mServiceManager;

    @Inject
    public UpdatePhonePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestCode(String phone) {
        mServiceManager.getUserService()
                .sendCode(phone, Constant.CHM)
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
                        mView.codeSuccess();
                    }
                });
    }

    @Override
    public void requestUpdatePhone(String phone, String code) {
        mServiceManager.getUserService()
                .updatePhone(phone,code)
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
                        mView.updatePhoneSuccess(phone);
                    }
                });
    }
}
