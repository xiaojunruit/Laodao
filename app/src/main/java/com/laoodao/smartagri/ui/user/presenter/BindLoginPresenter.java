package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.BindLoginContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/28.
 */

public class BindLoginPresenter extends RxPresenter<BindLoginContract.BindLoginView> implements BindLoginContract.Presenter<BindLoginContract.BindLoginView>{
    ServiceManager mServiceManger;
    @Inject
    public BindLoginPresenter(ServiceManager serviceManager){
        this.mServiceManger=serviceManager;
    }

    @Override
    public void requestBindLogin(String platform, String openId, String accessToken, String tips, int type, String phone, String code, String pwd) {
        mServiceManger.getUserService()
                .login3rd(platform,openId,accessToken,tips,type,phone,code,pwd)
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
                        mView.bindLoginSuccess(result.data);
                    }
                });
    }
}
