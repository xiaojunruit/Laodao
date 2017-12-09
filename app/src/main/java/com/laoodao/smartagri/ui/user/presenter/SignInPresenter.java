package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Calendar;
import com.laoodao.smartagri.bean.SignIn;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.SignInContract;
import com.laoodao.smartagri.utils.SpecialCalendar;
import com.laoodao.smartagri.utils.UiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/4/25.
 */

public class SignInPresenter extends RxPresenter<SignInContract.SignInView> implements SignInContract.Presenter<SignInContract.SignInView> {


    //闰年
    private boolean mIsLeapyear;
    private SpecialCalendar mSpecialCalendar;
    private SimpleDateFormat mDateFormat;

    ServiceManager mServiceManager;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Inject
    public SignInPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
        mSpecialCalendar = new SpecialCalendar();
        mDateFormat = new SimpleDateFormat("yyy-M-d", Locale.CANADA);

        Date date = new Date();
        //获取年月日
        String currentDate = mDateFormat.format(date);
        mYear = Integer.parseInt(currentDate.split("-")[0]);
        mMonth = Integer.parseInt(currentDate.split("-")[1]);
        mDay = Integer.parseInt(currentDate.split("-")[2]);
    }


    private List<Calendar> getDates(int week, int daysCount, int lastCount) {
        List<Calendar> calendarList = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < 42; i++) {
            Calendar calendar = new Calendar();
            calendar.year = mYear;

            if (i < week) {
                int temp = lastCount - week + 1;
                calendar.day = temp + i;
            } else if (i < daysCount + week) {
                //本月
                calendar.day = i - week + 1;
                calendar.isCurrent = true;
                calendar.month = mMonth;
            } else {
                //下个月
                calendar.day = j;
                j++;
            }
            calendarList.add(calendar);
        }
        return calendarList;
    }

    @Override
    public void getCalender() {
        getCalender(mYear, mMonth);
    }


    private void getCalender(int year, int month) {
        //是否是闰年
        boolean isLeapyear = mSpecialCalendar.isLeapYear(year);
        //当前月的总天数
        int daysCount = mSpecialCalendar.getDaysOfMonth(isLeapyear, month);
        //某月的第一天是星期几1
        int week = mSpecialCalendar.getWeekdayOfMonth(year, month);
        //上个月的总天数
        int lastCount = mSpecialCalendar.getDaysOfMonth(isLeapyear, month - 1);

        List<Calendar> calendarList = getDates(week, daysCount, lastCount);
        mView.showCalender(calendarList);

        String date = UiUtils.getString(R.string.yyyy_M_dd, String.valueOf(year), String.valueOf(month));
        mView.showDate(date);

        getSignInInfo();
    }

    @Override
    public void previousMonth() {

        int temp = mMonth - 1;
        if (temp < 1) {
            mYear--;
            mMonth = 12;
        } else {
            mMonth--;
        }
        getCalender(mYear, mMonth);

    }

    @Override
    public void nextMonth() {
        int temp = mMonth + 1;
        if (temp > 12) {
            mYear++;
            mMonth = 1;
        } else {
            mMonth++;
        }
        getCalender(mYear, mMonth);
    }

    @Override
    public void signIn() {

        mServiceManager.getUserService()
                .signIn()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        mView.signInSuccess();
                    }
                });

    }

    @Override
    public void getSignInInfo() {
        mServiceManager.getUserService()
                .signInInfo(mYear, mMonth)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<SignIn>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<SignIn> result) {
                        mView.showSignIn(result.data);
                    }
                });
    }

    @Override
    public void requestPoint() {
        mServiceManager.getUserService()
                .getPoints()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<String> stringResult) {
                        mView.pointSuccess(stringResult.data.toString());
                    }
                });
    }
}
