package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.bean.base.Result;

import java.util.List;

/**
 * Created by WORK on 2017/4/13.
 */

public interface UserContract {
    interface UserView extends BaseView {
        void showMenuList(List<UserMenu> userMenuList);

        void showUserInfo(User user);

        void isNewMessage(NewMessage message);

        void pointSuccess(String point);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getMenuList();

        void getUserInfo();

        void requestMessage();
        void requestPoint();
    }
}
