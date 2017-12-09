package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.ContactsListContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/8/16.
 */

public class ContactsListPresenter extends RxPresenter<ContactsListContract.ChatListView> implements ContactsListContract.Presenter<ContactsListContract.ChatListView> {
    ServiceManager mServiceManager;

    @Inject
    public ContactsListPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getAvatar(String uids) {
        mServiceManager.getUserService()
                .getAvatar(uids)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<ChatAvatar>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<ChatAvatar>> listResult) {
                        mView.setAvatar(listResult.data);
                    }
                });
    }
}
