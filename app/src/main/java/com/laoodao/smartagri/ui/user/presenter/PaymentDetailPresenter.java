package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.PaymentDetail;
import com.laoodao.smartagri.ui.user.contract.PaymentDetailContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/5/2.
 */

public class PaymentDetailPresenter extends RxPresenter<PaymentDetailContract.PaymentDetailView> implements PaymentDetailContract.Presenter<PaymentDetailContract.PaymentDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public PaymentDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestPaymentDetail(int page) {
        List<PaymentDetail> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new PaymentDetail());
        }
        mView.paymentDetailSuccess(list,true);
    }
}
