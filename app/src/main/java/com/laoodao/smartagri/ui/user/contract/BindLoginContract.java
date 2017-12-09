package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.User;

/**
 * Created by WORK on 2017/4/28.
 */

public interface BindLoginContract {
    interface BindLoginView extends BaseView {
        void bindLoginSuccess(User user);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestBindLogin(String platform, String openId, String accessToken, String tips, int type, String phone, String code, String pwd);
    }
}
