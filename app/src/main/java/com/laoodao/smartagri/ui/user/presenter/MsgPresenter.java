package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MsgDetail;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.MsgContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/4.
 */

public class MsgPresenter extends RxPresenter<MsgContract.MsgView> implements MsgContract.Presenter<MsgContract.MsgView> {
    ServiceManager mServiceManager;

    @Inject
    public MsgPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
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
    public void clearMsg(int type) {
        mServiceManager.getUserService()
                .clearMsg(type)
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
                        mView.clearMsgSuccess();
                    }
                });
    }


}
