package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.user.contract.ReplyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/4.
 */

public class ReplyPresenter extends ListPresenter<ReplyContract.ReplyView> implements ReplyContract.Presenter<ReplyContract.ReplyView> {
    ServiceManager mServiceManager;

    @Inject
    public ReplyPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestReply(int page, int type) {
        mServiceManager.getUserService().getMyMessage(page, type)
                .compose(transform())
                .subscribe(new Subscriber<Page<Notice>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<Notice> noticePage) {
                        onPageLoaded(noticePage);
                        mView.replySuucess();
                    }
                });
    }
}
