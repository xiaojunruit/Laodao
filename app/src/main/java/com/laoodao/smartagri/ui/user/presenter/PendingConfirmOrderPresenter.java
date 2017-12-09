package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.PendingConfirmOrderContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/2.
 */

public class PendingConfirmOrderPresenter extends ListPresenter<PendingConfirmOrderContract.PendingConfirmOrderView> implements PendingConfirmOrderContract.Presenter<PendingConfirmOrderContract.PendingConfirmOrderView> {
    ServiceManager mServiceManager;

    @Inject
    public PendingConfirmOrderPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestPendingConfirmOrder(int page) {
        mServiceManager.getUserService()
                .myOrder(page, Constant.PENDING_CONFIRMATION)
                .compose(transform())
                .subscribe(new Subscriber<Page<MyOrder>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->"+e.getMessage());
                    }

                    @Override
                    public void onNext(Page<MyOrder> myOrderPage) {
                        onPageLoaded(myOrderPage);
                        mView.pendingConfirmOrderSuccess(myOrderPage.data.items);
                    }
                });
    }
}
