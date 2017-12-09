package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.discovery.contract.MapFeedbackContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/8/9.
 */

public class MapFeedbackPresenter extends RxPresenter<MapFeedbackContract.MapFeedbackView> implements MapFeedbackContract.Presenter<MapFeedbackContract.MapFeedbackView> {
    ServiceManager mServiceManager;

    @Inject
    public MapFeedbackPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void addSuggest(String content, int type, int id, String phone) {
        mServiceManager.getUserService()
                .addSuggest(content, type, id, phone)
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
                        mView.success();
                    }
                });
    }
}
