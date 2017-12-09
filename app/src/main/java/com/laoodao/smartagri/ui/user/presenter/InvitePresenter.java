package com.laoodao.smartagri.ui.user.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.InviteInfo;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.InviteContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class InvitePresenter extends RxPresenter<InviteContract.InviteView> implements InviteContract.Presenter<InviteContract.InviteView> {
    ServiceManager mServiceManager;

    @Inject
    public InvitePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getInviteInfo() {
        Observable<Result<InviteInfo>> fromWork = mServiceManager.getUserService().inviteInfo().compose(RxUtils.rxCacheListHelper(Constant.LAOODAO_SHARE));
        Observable<Result<InviteInfo>> observable = RxUtils.rxCreateDiskObservable(Constant.LAOODAO_SHARE, new TypeToken<Result<InviteInfo>>() {
        }.getType());
        Observable.concat(observable, fromWork)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<InviteInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<InviteInfo> inviteInfoResult) {
                        mView.showInviteInfo(inviteInfoResult.data);
                    }
                });
//        mServiceManager.getUserService()
//                .inviteInfo()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<InviteInfo>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("onError->" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Result<InviteInfo> inviteInfoResult) {
//                        mView.showInviteInfo(inviteInfoResult.data);
//                    }
//                });
    }

    @Override
    public void shareBack(String tag, int id) {
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
                    public void onNext(Result<String> stringResult) {

                    }
                });
    }
}
