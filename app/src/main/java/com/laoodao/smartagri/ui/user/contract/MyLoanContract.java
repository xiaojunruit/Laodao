package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.MyLoan;

/**
 * Created by Long-PC on 2017/4/13.
 */

public interface MyLoanContract {
    interface MyLoanView extends BaseView{
        void myLoadSuccess(MyLoan myLoan);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestMyLoan();
    }
}
