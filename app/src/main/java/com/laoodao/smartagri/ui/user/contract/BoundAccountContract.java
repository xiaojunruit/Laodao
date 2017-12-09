package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/15.
 * 账号与密码Contact
 */

public interface BoundAccountContract {
    interface BoundAccountView extends BaseView {
        void thirdSuccess(String platform,String nickName);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestThird(String platform, String openid, String token, int type);
    }
}
