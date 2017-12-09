package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.PaymentHistory;

import java.util.List;

/**
 * Created by WORK on 2017/5/2.
 */

public interface PaymentHistoryListContract {
    interface PaymentHistoryView extends BaseView{
        void paymentHistorySuccess(List<PaymentHistory> paymentHistories, boolean isRefresh);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestPaymentHistory(int page);
    }
}
