package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/15.
 * 修改密码Contact
 */

public interface UpdatePwdContract {
    interface UpdatePwdView extends BaseView{
        void updatePwdSuccess();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void postPwdData(String oldPwd,String newPwd,String rePwd);
        void logout();
    }
}
