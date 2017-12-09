package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.discovery.contract.ReleaseFormulaContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/19.
 */

public class ReleaseFormulaPresenter extends RxPresenter<ReleaseFormulaContract.ReleaseFormulaView> implements ReleaseFormulaContract.Presenter<ReleaseFormulaContract.ReleaseFormulaView> {
    ServiceManager mServiceManager;

    @Inject
    public ReleaseFormulaPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestReleaseFormula(String cropName, String disease, String content, int id) {
        mServiceManager.getDiscoverService()
                .releaseFormula(cropName, disease, content, id)
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
                        mView.releaseFormulaSuccess();
                    }
                });
    }
}
