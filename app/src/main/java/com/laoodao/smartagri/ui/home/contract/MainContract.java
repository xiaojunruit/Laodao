package com.laoodao.smartagri.ui.home.contract;

import android.content.Intent;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.bean.NewMessage;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface MainContract {

    interface MainView extends BaseView {
        void showMessage(NewMessage message);
        void loginHuanXin();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requsetMessage();
        void requestInitMenu();

        void share(String tag,int id);
    }


}
