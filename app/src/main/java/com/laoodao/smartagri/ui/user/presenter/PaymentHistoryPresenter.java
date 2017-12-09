package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.PaymentHistory;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.user.contract.PaymentHistoryContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.internal.util.unsafe.MpmcArrayQueue;

/**
 * Created by WORK on 2017/4/28.
 */

public class PaymentHistoryPresenter extends ListPresenter<PaymentHistoryContract.PaymentHistoryView> implements PaymentHistoryContract.Presenter<PaymentHistoryContract.PaymentHistoryView> {
    ServiceManager mServiceManager;

    @Inject
    public PaymentHistoryPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestPaymentHistory(int page) {
        mServiceManager.getUserService()
                .requestPaymentHistory(page)
                .compose(transform())
                .subscribe(new Subscriber<Page<PaymentHistory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<PaymentHistory> paymentHistoryPage) {
                        onPageLoaded(paymentHistoryPage);
                        mView.commit();
                    }
                });
    }
}
