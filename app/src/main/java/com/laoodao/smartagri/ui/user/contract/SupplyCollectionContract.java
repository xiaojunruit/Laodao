package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;


/**
 * Created by Administrator on 2017/8/19 0019.
 */

public interface SupplyCollectionContract {


    interface BuyView extends ListBaseView {

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(int memberId, int type, int page);
    }
}