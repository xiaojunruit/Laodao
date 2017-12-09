package com.laoodao.smartagri.ui.discovery.contract;

import android.app.Activity;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.cotton.Cotton;

import rx.Observable;

/**
 * Created by WORK on 2017/7/21.
 */

public interface RecordBillContract {
    interface RecordBillView extends BaseView {
        void setCottonBill(CottonBill cotton);

        void cottonSusscess(Cotton Cotton);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestCottonBill(String num,String info);

        void cottonSelect(Activity activity, String url, String searchCode, String validateCode, String srarchYear);
    }
}
