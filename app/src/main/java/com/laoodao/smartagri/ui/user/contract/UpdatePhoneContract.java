package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/15.
 */

public interface UpdatePhoneContract {
    interface UpdatePhoneView extends BaseView{
        void codeSuccess();
        void updatePhoneSuccess(String phone);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestCode(String phone);
        void requestUpdatePhone(String phone,String code);
    }
}
