package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.User;

/**
 * Created by WORK on 2017/5/4.
 */

public interface FastRegisterContract {
    interface FastRegisterView extends BaseView{
        void countdown();
        void registerSuccess(User data);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getCode(String phone,String rule);
        void register(String token, String platform, String openId, String tips,String username,String pwd,String code,int type);
    }
}
