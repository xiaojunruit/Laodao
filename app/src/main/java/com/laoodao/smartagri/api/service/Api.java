package com.laoodao.smartagri.api.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;
import com.laoodao.smartagri.utils.UiUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/4/21.
 */

public class Api {


    //  public static final String BASE_URL = "https://apiv10.laoodao.cn/";

    public static final String BASE_URL = "https://ldapi.binarynt.com/index.php/";


    public static <T extends Response> Observable.Transformer<T, T> checkOn(BaseView baseView) {
        return observable -> observable.compose(_checkOn()).compose(RxUtils.bindToLifecycle(baseView));
    }

    private static <T extends Response> Observable.Transformer<T, T> _checkOn() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> doCheck(result))
                .onErrorResumeNext(throwable -> doCatch(throwable));
    }

    public static boolean doCheck(Response result) {
        if (result == null) {
            UiUtils.makeText("网络错误");
            return false;
        }
        if (!TextUtils.isEmpty(result.message)) {
            UiUtils.makeText(result.message);
        }
        if (result.code == 401) {
            Global.getInstance().logout();
            UiUtils.startActivity(LoginActivity.class);
            return false;
        }
        return result.code == 0;
    }

    private static <T> Observable<T> doCatch(Throwable throwable) {

        Log.e("throwable", throwable.toString());
        throwable.printStackTrace();

        return Observable.empty();
    }
}
