package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.BaikeAllMenu;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.FindCropContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/19.
 */

public class FindCropPresenter extends RxPresenter<FindCropContract.FindCropView> implements FindCropContract.Presenter<FindCropContract.FindCropView> {


    ServiceManager mServiceManager;

    @Inject
    public FindCropPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestSelectCrop() {
        mServiceManager.getDiscoverService()
                .getBaikeMenu(1)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<BaikeMenu>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<BaikeMenu> menuResult) {
                        mView.showSelectCrop(menuResult.data);
                        requestAllCrop();
                    }
                });
    }

    @Override
    public void requestAllCrop() {
        mServiceManager.getDiscoverService()
                .getBaikeAllMenu(2)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<BaikeAllMenu>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.onError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<BaikeAllMenu> baikeAllMenuResult) {
                        if (baikeAllMenuResult == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.shouAllCrop(baikeAllMenuResult.data);
                    }
                });
    }

    @Override
    public void requestAddCrop(String gcPaths) {
        mServiceManager.getDiscoverService()
                .setMemberBaikeMunu(gcPaths)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.addCrop();
                    }
                });
    }

}