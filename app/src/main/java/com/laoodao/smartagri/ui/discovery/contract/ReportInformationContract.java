package com.laoodao.smartagri.ui.discovery.contract;

import android.app.Activity;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.bean.cotton.CottonData;

import java.util.List;

import rx.Observable;


/**
 * Created by Administrator on 2017/6/28.
 */

public interface ReportInformationContract {


    interface ReportInformationView extends BaseView {
        void cottonSusscess(Cotton Cotton);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void cottonSelect(Activity activity,String url, String searchCode, String validateCode, String srarchYear);
    }
}