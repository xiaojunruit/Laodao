package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.market.contact.MyReleaseContact;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyReleasePresenter extends RxPresenter<MyReleaseContact.MyReleaseView> implements MyReleaseContact.Presenter<MyReleaseContact.MyReleaseView> {
    ServiceManager mServiceManager;

    @Inject
    public MyReleasePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void share(String tag, int id) {
        mServiceManager.getHomeService()
                .shareBack(tag, id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<String> response) {

                    }
                });
    }
}
