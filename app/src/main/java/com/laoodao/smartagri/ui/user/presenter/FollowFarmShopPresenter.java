package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.WonderStore;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.FollowFarmShopContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class FollowFarmShopPresenter extends ListPresenter<FollowFarmShopContract.FollowFarmShopView> implements FollowFarmShopContract.Presenter<FollowFarmShopContract.FollowFarmShopView> {


    ServiceManager mServiceManager;

    @Inject
    public FollowFarmShopPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList(String memberId, int page) {
        mServiceManager.getUserService()
                .getWonderStore(page, memberId)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }

    @Override
    public void AddOrDelFollow(String id) {
        mServiceManager.getUserService()
                .addOrDelStore(id)
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

                    }
                });
    }
}