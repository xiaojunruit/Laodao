package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.discovery.contract.SelectNewsMenuContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/18.
 */

public class SelectNewsMenuPresenter extends RxPresenter<SelectNewsMenuContract.SelectNewsMenuView> implements SelectNewsMenuContract.Presenter<SelectNewsMenuContract.SelectNewsMenuView> {
    ServiceManager mServiceManager;

    @Inject
    public SelectNewsMenuPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestNewsMenu() {
        mServiceManager.getDiscoverService()
                .getNewsMenu()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<MechanicalType>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<MechanicalType> mechanicalTypePage) {
                        mView.newsMenuSuccess(mechanicalTypePage.data.items);
                    }
                });
    }

    @Override
    public void requestNewsMenuIds(String ids) {
        mServiceManager.getDiscoverService()
                .postNewsMenu(ids)
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
                        mView.newsMenuIdsSuucess();
                    }
                });
    }


}
