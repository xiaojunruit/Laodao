package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.user.contract.UpdateNickNameContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class UpdateNickNamePresenter extends RxPresenter<UpdateNickNameContract.BasicInfoView> implements UpdateNickNameContract.Presenter<UpdateNickNameContract.BasicInfoView> {
    ServiceManager mServiceManager;

    @Inject
    public UpdateNickNamePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void updateNickName(String nickName) {
        mServiceManager.getUserService()
                .updateNickName(nickName)
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
                        mView.updateSuccess();
                    }
                });
    }
}
