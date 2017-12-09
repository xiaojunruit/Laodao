package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/7/21.
 */

public interface InvitingFriendsContract {
    interface InvitingFriendsView extends BaseView{
        void checkFirst(int share);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void cottonBack(String tag,int id);
    }
}
