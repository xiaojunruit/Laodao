package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.User;

/**
 * Created by WORK on 2017/4/15.
 */
public interface RegisterContract {
    interface RegisterView extends BaseView{
        void countdown();

        void registerSuccess(User data);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getCode(String phone,String rule);
        void register(String username,String pwd,String code);
    }
}
