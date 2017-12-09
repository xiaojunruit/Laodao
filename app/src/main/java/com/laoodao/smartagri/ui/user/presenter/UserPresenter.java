package com.laoodao.smartagri.ui.user.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.UserContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/13.
 */

public class UserPresenter extends RxPresenter<UserContract.UserView> implements UserContract.Presenter<UserContract.UserView> {


    ServiceManager mServiceManager;


    @Inject
    public UserPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void getMenuList() {
        String key = Constant.USER_MENU;
        Observable<Result<List<UserMenu>>> fromNetWork = mServiceManager.getUserService().getUserMenu().compose(RxUtils.rxCacheListHelper(key));
        Observable.concat(RxUtils.rxCreateDiskObservable(key, new TypeToken<Result<List<UserMenu>>>() {
        }.getType()), fromNetWork).compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<UserMenu>>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<UserMenu>> result) {
                        mView.showMenuList(result.data);
                    }
                });

//        mServiceManager.getUserService()
//                .getUserMenu()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<List<UserMenu>>>() {
//                    @Override
//                    public void onCompleted() {
//                        mView.complete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Result<List<UserMenu>> result) {
//                        mView.showMenuList(result.data);
//                    }
//                });
    }

    @Override
    public void getUserInfo() {
        String key = Constant.USER_INFO_LIST;
        Observable<Result<User>> fromNetWork = mServiceManager.getUserService().getUserInfo().compose(RxUtils.rxCacheListHelper(key));
        Observable.concat(RxUtils.rxCreateDiskObservable(key, new TypeToken<Result<User>>() {
        }.getType()), fromNetWork).compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<User> result) {
                        mView.showUserInfo(result.data);
                    }
                });

//        mServiceManager.getUserService()
//                .getUserInfo()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<User>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Result<User> result) {
//                        mView.showUserInfo(result.data);
//                    }
//                });
    }

    @Override
    public void requestMessage() {
        mServiceManager.getUserService()
                .isNewMessage()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<NewMessage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<NewMessage> newMessageResult) {
                        mView.isNewMessage(newMessageResult.data);
                    }
                });
    }

    @Override
    public void requestPoint() {
        mServiceManager.getUserService()
                .getPoints()
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
                        mView.pointSuccess(stringResult.data.toString());
                    }
                });
    }

}
