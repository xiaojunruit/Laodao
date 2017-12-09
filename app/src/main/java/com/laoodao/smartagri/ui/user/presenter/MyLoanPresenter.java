package com.laoodao.smartagri.ui.user.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.MyLoan;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.MyLoanContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class MyLoanPresenter extends RxPresenter<MyLoanContract.MyLoanView> implements MyLoanContract.Presenter<MyLoanContract.MyLoanView> {
    ServiceManager mServiceManager;

    @Inject
    public MyLoanPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestMyLoan() {
        String key = Constant.MY_LOAN;
        Observable<Result<MyLoan>> fromNetWork = mServiceManager.getUserService().myLoan().compose(RxUtils.rxCacheListHelper(key));
        Observable.concat(RxUtils.rxCreateDiskObservable(key, new TypeToken<Result<MyLoan>>() {
        }.getType()), fromNetWork)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<MyLoan>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<MyLoan> myLoanResult) {
                        mView.myLoadSuccess(myLoanResult.data);
                    }
                });

//        mServiceManager.getUserService()
//                .myLoan()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<MyLoan>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("onError->"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Result<MyLoan> myLoanResult) {
//                        mView.myLoadSuccess(myLoanResult.data);
//                    }
//                });
    }
}
