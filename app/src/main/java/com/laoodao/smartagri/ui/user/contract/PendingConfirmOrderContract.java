package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MyOrder;

import java.util.List;

/**
 * Created by WORK on 2017/5/2.
 */

public interface PendingConfirmOrderContract {
    interface PendingConfirmOrderView extends ListBaseView {
        void pendingConfirmOrderSuccess(List<MyOrder> myOrders);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestPendingConfirmOrder(int page);
    }
}
