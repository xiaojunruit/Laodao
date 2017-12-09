package com.laoodao.smartagri.ui.user.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.LoanRecord;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.LoanRecordListContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/14.
 */

public class LoanRecordListPresenter extends ListPresenter<LoanRecordListContract.LoanRecordListView> implements LoanRecordListContract.Presenter<LoanRecordListContract.LoanRecordListView> {
    ServiceManager mServiceManager;

    @Inject
    public LoanRecordListPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestLoadRecord() {
        String key = Constant.LOAD_RECORD;
        Observable<Result<LoanRecord>> fromNetWork = mServiceManager.getUserService().loanRecord().compose(RxUtils.rxCacheListHelper(key));
        Observable.concat(RxUtils.rxCreateDiskObservable(key, new TypeToken<Result<LoanRecord>>() {
        }.getType()), fromNetWork)
                .compose(transform())
                .subscribe(new Subscriber<Result<LoanRecord>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<LoanRecord> loanRecordResult) {
                        if (!NetworkUtils.isAvailable(LDApplication.getInstance()) && TextUtils.isEmpty(loanRecordResult.data.contractNo)) {
                            mView.onError();
                        } else if (TextUtils.isEmpty(loanRecordResult.data.contractNo)) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.loadRecordSuccess(loanRecordResult.data);
                    }
                });
//        mServiceManager.getUserService()
//                .loanRecord()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<LoanRecord>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.onError();
//                    }
//
//                    @Override
//                    public void onNext(Result<LoanRecord> loanRecordResult) {
//                        if (TextUtils.isEmpty(loanRecordResult.data.contractNo)) {
//                            mView.onEmpty();
//                        } else {
//                            mView.onContent();
//                        }
//                        mView.loadRecordSuccess(loanRecordResult.data);
//                    }
//                });
    }
}
