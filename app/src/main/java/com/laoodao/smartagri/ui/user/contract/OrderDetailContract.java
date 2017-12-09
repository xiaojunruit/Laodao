package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MyOrderDetail;
import com.laoodao.smartagri.bean.PaymentDetail;

import java.util.List;

/**
 * Created by WORK on 2017/4/29.
 */

public interface OrderDetailContract {
    interface OrderDetailView extends ListBaseView {
        void orderDetailSuccess(MyOrderDetail detail);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestOrderDetail(int id);
    }
}
