package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.PaymentHistory;

import java.util.List;

/**
 * Created by WORK on 2017/4/28.
 */

public interface PaymentHistoryContract {
    interface PaymentHistoryView extends ListBaseView{
        void commit();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestPaymentHistory(int page);
    }
}
