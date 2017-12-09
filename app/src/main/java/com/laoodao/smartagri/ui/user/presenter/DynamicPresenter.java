package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.user.contract.DynamicContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/4.
 */

public class DynamicPresenter extends ListPresenter<DynamicContract.DynamicView> implements DynamicContract.Presenter<DynamicContract.DynamicView> {
    ServiceManager mServiceManager;

    @Inject
    public DynamicPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDynamic(int page, int type) {
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
                        mView.DynamicSuccess();
                    }
                });
    }
}
