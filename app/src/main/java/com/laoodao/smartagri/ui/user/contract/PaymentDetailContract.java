package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.PaymentDetail;

import java.util.List;

/**
 * Created by WORK on 2017/5/2.
 */

public interface PaymentDetailContract {
    interface PaymentDetailView extends BaseView {
        void paymentDetailSuccess(List<PaymentDetail> list,boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestPaymentDetail(int page);
    }
}
