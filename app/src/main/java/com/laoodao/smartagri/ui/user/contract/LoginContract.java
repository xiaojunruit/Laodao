package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Result;

/**
 * Created by WORK on 2017/4/15.
 */

public interface LoginContract {
    interface LoginView extends BaseView {
        void loginSuccess(User user);

        void login3rdSuccess(User user);

        void jointLogin(String avatar, String nickName, String platform, String openId, String accessToken, String tips);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void login(String username, String password,String pushId);

        void login3rd(String platform, String openId, String accessToken, String tips);
    }
}
