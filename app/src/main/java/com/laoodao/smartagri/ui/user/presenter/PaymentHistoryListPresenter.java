package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.PaymentHistory;
import com.laoodao.smartagri.ui.user.contract.PaymentHistoryListContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/5/2.
 */

public class PaymentHistoryListPresenter extends RxPresenter<PaymentHistoryListContract.PaymentHistoryView> implements PaymentHistoryListContract.Presenter<PaymentHistoryListContract.PaymentHistoryView> {
    ServiceManager mServiceManager;

    @Inject
    public PaymentHistoryListPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestPaymentHistory(int page) {
        List<PaymentHistory> paymentHistories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            paymentHistories.add(new PaymentHistory());
        }
        mView.paymentHistorySuccess(paymentHistories, true);
    }
}
