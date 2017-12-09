package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.TradeRecordDetailContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/19.
 */

public class TradeRecordDetailPresenter extends RxPresenter<TradeRecordDetailContract.TradeRecordDetailView> implements TradeRecordDetailContract.Presenter<TradeRecordDetailContract.TradeRecordDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public TradeRecordDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestPayment(int id) {
        mServiceManager.getUserService()
                .tradeRecordDetial(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<TradeRecord>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<TradeRecord> tradeRecordResult) {
                        mView.tradeRecodeDetailSucess(tradeRecordResult.data);

                    }
                });
    }

    @Override
    public void tradeCode(int id) {
        mServiceManager.getUserService()
                .tradeCode(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.tradeCodeSuccess();
                    }
                });
    }

    @Override
    public void tradePay(int id, String code) {
        mServiceManager.getUserService()
                .tradePay(id, code)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.tradePaySuccess();
                    }
                });
    }
}
