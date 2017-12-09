package com.laoodao.smartagri.ui.user.presenter;

import android.app.Service;
import android.util.Log;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyMessage;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.NoticeContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/4.
 */

public class NoticePresenter extends ListPresenter<NoticeContract.NoticeView> implements NoticeContract.Presenter<NoticeContract.NoticeView> {
    ServiceManager mServiceManager;

    @Inject
    public NoticePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestNotice(int page, int type) {

        mServiceManager.getUserService().getMyMessage(page, type)
                .compose(transform())
                .subscribe(this::onPageLoaded);
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
    public void readMsg(int id) {
        mServiceManager.getUserService()
                .readMsg(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result result) {
                        mView.readMsgSucess(result.data.toString().trim());
                    }
                });
    }
}
