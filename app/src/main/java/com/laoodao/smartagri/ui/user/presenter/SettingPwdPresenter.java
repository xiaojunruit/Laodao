package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.SettingPwdContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/21.
 */

public class SettingPwdPresenter extends RxPresenter<SettingPwdContract.SettingPwdView> implements SettingPwdContract.Presenter<SettingPwdContract.SettingPwdView> {
    ServiceManager mServiceManager;

    @Inject
    public SettingPwdPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
    @Override
    public void findPassword(String token, String pwd, String rePwd) {
        mServiceManager.getUserService()
                .findPassword(token, pwd, rePwd)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->"+e.getMessage());
                    }

                    @Override
                    public void onNext(Result<User> result) {
                        mView.settingSuccess();
                    }
                });
    }
}
