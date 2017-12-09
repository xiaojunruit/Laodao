package com.laoodao.smartagri.ui.farmland.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.base.AccountDetail;
import com.laoodao.smartagri.bean.base.Response;


/**
 * Created by Administrator on 2017/4/25.
 */

public interface AccountDetailContract {


    interface AccountDetailView extends BaseView {
        void initAccountDetail(AccountDetail detail);

        void getResult(Response response);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestAccountDetail(int id);

        void requestResult(int id);
    }
}