package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/21.
 */

public interface ValidatePhoneContract {
    interface ValidatePhoneView extends BaseView{
        void setToken(String token);
        void countdown();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void validateCode(String mobile,String code);
        void getCode(String mobile,String rule);
    }
}
