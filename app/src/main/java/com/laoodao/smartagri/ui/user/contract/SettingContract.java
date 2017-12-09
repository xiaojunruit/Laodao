package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by Long-PC on 2017/4/13.
 */

public interface SettingContract {
    interface SettingView extends BaseView{
        void logoutSuccess();
        void msgPushSuccess();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void logout();
        void settingPush(int isPush);
    }
}
