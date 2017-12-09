package com.laoodao.smartagri.ui.discovery.presenter;

import android.app.Activity;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.ui.discovery.contract.RecordBillContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.ProgressOperator;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/7/21.
 */

public class RecordBillPresenter extends RxPresenter<RecordBillContract.RecordBillView> implements RecordBillContract.Presenter<RecordBillContract.RecordBillView> {
    ServiceManager mServiceManager;

    @Inject
    public RecordBillPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestCottonBill(String num,String info) {
        mServiceManager.getDiscoverService()
                .cottonBill(num,info)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<CottonBill>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Result<CottonBill> cottonBillResult) {
                        mView.setCottonBill(cottonBillResult.data);
                    }
                });
    }

    @Override
    public void cottonSelect(Activity activity, String url, String searchCode, String validateCode, String srarchYear) {
        mServiceManager.getMarketService().cottonSelect(url, searchCode, validateCode, srarchYear)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
