package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.Calendar;
import com.laoodao.smartagri.bean.SignIn;
import com.laoodao.smartagri.bean.base.Result;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/25.
 */

public interface SignInContract {


    interface SignInView extends BaseView {

        void showCalender(List<Calendar> calendarList);

        void showDate(String date);

        void signInSuccess();

        void showSignIn(SignIn result);

        void pointSuccess(String point);
    }

    interface Presenter<T> extends BasePresenter<T> {


        void getCalender();

        void previousMonth();

        void nextMonth();


        void signIn();

        void getSignInInfo();

        void requestPoint();
    }
}