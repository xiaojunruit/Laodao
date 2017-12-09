package com.laoodao.smartagri.ui.user.presenter;

import android.graphics.Paint;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.user.contract.UpdatePwdContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class UpdatePwdPresenter extends RxPresenter<UpdatePwdContract.UpdatePwdView> implements UpdatePwdContract.Presenter<UpdatePwdContract.UpdatePwdView> {
    ServiceManager mServiceManager;

    @Inject
    public UpdatePwdPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void postPwdData(String oldPwd, String newPwd, String rePwd) {
        mServiceManager.getUserService()
                .updatePassword(oldPwd, newPwd, rePwd)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.updatePwdSuccess();
                    }
                });
    }

    @Override
    public void logout() {
        mServiceManager.getUserService()
                .logout()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.updatePwdSuccess();
                    }
                });
    }


}
