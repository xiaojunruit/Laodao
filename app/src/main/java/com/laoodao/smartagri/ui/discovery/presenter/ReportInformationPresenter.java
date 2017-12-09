package com.laoodao.smartagri.ui.discovery.presenter;

import android.app.Activity;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.ui.discovery.contract.ReportInformationContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.ProgressOperator;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/6/28.
 */

public class ReportInformationPresenter extends RxPresenter<ReportInformationContract.ReportInformationView> implements ReportInformationContract.Presenter<ReportInformationContract.ReportInformationView> {


    ServiceManager mServiceManager;

    @Inject
    public ReportInformationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void cottonSelect(Activity activity,String url, String searchCode, String validateCode, String srarchYear) {
        mServiceManager.getMarketService().cottonSelect(url, searchCode, validateCode, srarchYear)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(new ProgressOperator<>(activity, "正在查询..."))
                .subscribe(new Subscriber<Cotton>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Cotton cotton) {
                        mView.cottonSusscess(cotton);
                    }
                });
    }
}