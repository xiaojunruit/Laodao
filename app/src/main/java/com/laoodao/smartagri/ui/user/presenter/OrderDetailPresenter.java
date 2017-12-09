package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.MyOrderDetail;
import com.laoodao.smartagri.bean.PaymentDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.OrderDetailContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/29.
 */

public class OrderDetailPresenter extends ListPresenter<OrderDetailContract.OrderDetailView> implements OrderDetailContract.Presenter<OrderDetailContract.OrderDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public OrderDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestOrderDetail(int id) {
        mServiceManager.getUserService()
                .getOrderDetail(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<MyOrderDetail>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<MyOrderDetail> myOrderDetailResult) {
                        if (myOrderDetailResult==null){
                            mView.onError();
                        }
                        mView.onContent();
                        mView.orderDetailSuccess(myOrderDetailResult.data);
                    }
                });
    }
}
