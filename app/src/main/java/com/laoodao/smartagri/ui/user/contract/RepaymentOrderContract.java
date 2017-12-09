package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.RepaymentOrder;

/**
 * Created by WORK on 2017/7/6.
 */

public interface RepaymentOrderContract {
    interface RepaymentOrderView extends ListBaseView {
        void setRepaymentOrder(RepaymentOrder repaymentOrder);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestRepaymentOrderList(int page);
    }
}
