package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.NearbyShopDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.NearbyShopDetailContract;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;

/**
 * Created by WORK on 2017/5/3.
 */

public class NearbyShopDetailPresenter extends ListPresenter<NearbyShopDetailContract.NearbyShopDetailView> implements NearbyShopDetailContract.Presenter<NearbyShopDetailContract.NearbyShopDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public NearbyShopDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestNearbyShopDetail(String id, String lat, String lng) {
        mServiceManager.getDiscoverService()
                .getStoreInfo(id, lat, lng)
                .compose(transform())
                .subscribe(new Subscriber<Result<NearbyShopDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<NearbyShopDetail> nearbyShopDetailResult) {
                        mView.nearbyShopDetailSuccess(nearbyShopDetailResult.data);
                        if (nearbyShopDetailResult.data == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                    }
                });
    }

    @Override
    public void requestFollow(String id) {
        mServiceManager.getDiscoverService()
                .addStroeFollow(id)
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
                        mView.successFollow();
                    }
                });
    }
}
