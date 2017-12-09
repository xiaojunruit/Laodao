package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.user.contract.IntroductionContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class IntroductionPresenter extends RxPresenter<IntroductionContract.IntroductionView> implements IntroductionContract.Presenter<IntroductionContract.IntroductionView> {


    ServiceManager mServiceManager;

    @Inject
    public IntroductionPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void updateSignature(String signature) {
        mServiceManager.getUserService()
                .updateSignature(signature)
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
                        mView.updateSignatureSuccess(signature);
                    }
                });
    }
}