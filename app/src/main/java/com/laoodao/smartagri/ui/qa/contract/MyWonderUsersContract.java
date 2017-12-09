package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;


/**
 * Created by Administrator on 2017/8/22 0022.
 */

public interface MyWonderUsersContract {


    interface MyWonderUsersView extends ListBaseView {
        void FollowSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(int memberId, int page, int type);

        void Follow(int memberId);
    }
}