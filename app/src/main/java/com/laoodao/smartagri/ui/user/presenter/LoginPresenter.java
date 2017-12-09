package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.user.contract.LoginContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/4/15.
 */

public class LoginPresenter extends RxPresenter<LoginContract.LoginView> implements LoginContract.Presenter<LoginContract.LoginView> {
    ServiceManager mServiceManager;

    @Inject
    public LoginPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void login(String username, String password,String pushId) {
        mServiceManager.getUserService()
                .login(username, password,pushId)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<User> result) {
                        mView.loginSuccess(result.data);
                    }
                });
    }

    @Override
    public void login3rd(String platform, String openId, String accessToken, String tips) {
        mServiceManager.getUserService()
                .login3rd(platform, openId, accessToken, tips)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<User> result) {
                        if (result==null){
                            return;
                        }
                        if (result.code == 4001) {
                            mView.jointLogin(result.data.avatar, result.data.nickname,platform,openId,accessToken,tips);
                            return;
                        }
                        mView.login3rdSuccess(result.data);

                    }
                });
    }


}
