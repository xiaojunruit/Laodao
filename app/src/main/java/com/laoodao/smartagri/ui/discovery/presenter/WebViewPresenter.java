package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.WebViewContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/7/23.
 */

public class WebViewPresenter extends RxPresenter<WebViewContract.WebView> implements WebViewContract.Presenter<WebViewContract.WebView> {

    ServiceManager mServiceManager;

    @Inject
    public WebViewPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void shareBack(String tag,int id) {
        mServiceManager.getHomeService()
                .shareBack(tag, id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<String> response) {
                        mView.shareBackSuccess();
                    }
                });
    }
}
