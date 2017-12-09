package com.laoodao.smartagri.ui.discovery.presenter;

import android.app.Activity;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Enterprise.Enterprise;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddress;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.EnterpriseInformationContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.ProgressOperator;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/6/28.
 */

public class EnterpriseInformationPresenter extends RxPresenter<EnterpriseInformationContract.EnterpriseInformationView> implements EnterpriseInformationContract.Presenter<EnterpriseInformationContract.EnterpriseInformationView> {


    ServiceManager mServiceManager;

    @Inject
    public EnterpriseInformationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void enterpriseSelect(Activity activity,String url, String enterprisecode, String validateCode, String enterprisename, String ASCRIPTIONA, String ASCRIPTIONB, String searchYear) {
        mServiceManager.getMarketService().enterpriseSelect(url, enterprisecode, validateCode, enterprisename, ASCRIPTIONA, ASCRIPTIONB, searchYear)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(new ProgressOperator<>(activity, "正在查询..."))
                .subscribe(new Subscriber<Enterprise>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Enterprise enterprise) {
                        if (enterprise!=null){
                            mView.setEnterprise(enterprise);
                        }
                    }
                });
    }

    @Override
    public void getCottonArea() {
        mServiceManager.getMarketService().getCottonArea()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<EnterpriseAddress>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<EnterpriseAddress>> listResult) {
                        mView.setEnterpriseAddress(listResult.data);
                    }
                });
    }
}