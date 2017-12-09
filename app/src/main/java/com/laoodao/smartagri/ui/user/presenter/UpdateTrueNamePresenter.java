package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.user.contract.UpdateTrueNameContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class UpdateTrueNamePresenter extends RxPresenter<UpdateTrueNameContract.UpdateTrueNameView> implements UpdateTrueNameContract.Presenter<UpdateTrueNameContract.UpdateTrueNameView> {


    ServiceManager mServiceManager;

    @Inject
    public UpdateTrueNamePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestUpdate(String name) {
        mServiceManager.getUserService()
                .updateTrueName(name)
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
                        mView.successUpdate(name);
                    }
                });
    }
}