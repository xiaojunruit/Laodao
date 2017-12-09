package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.BoundAccountContract;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class BoundAccountPresenter extends RxPresenter<BoundAccountContract.BoundAccountView> implements BoundAccountContract.Presenter<BoundAccountContract.BoundAccountView> {
    ServiceManager mServiceManager;

    @Inject
    public BoundAccountPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestThird(String platform, String openid, String token, int type) {
        mServiceManager.getUserService()
                .bindThird(platform, openid, token, type)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> mapResult) {
                        mView.thirdSuccess(platform,mapResult.data.get("nickname"));
                    }
                });
    }
}
