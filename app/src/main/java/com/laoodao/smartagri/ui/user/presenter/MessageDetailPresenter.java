package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MsgDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.MessageDetailContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/6/22.
 */

public class MessageDetailPresenter extends RxPresenter<MessageDetailContract.MessageDetailView> implements MessageDetailContract.Presenter<MessageDetailContract.MessageDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public MessageDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
    @Override
    public void getMsgDetail(int id,String objType) {
        mServiceManager.getUserService()
                .getMsgDetail(id,objType)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<MsgDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<MsgDetail> msgDetailResult) {
                        mView.setMsgDetail(msgDetailResult.data);
                    }
                });
    }
}
