package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.RepaymentOrder;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.RepaymentOrderContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/7/6.
 */

public class RepaymentOrderPresenter extends ListPresenter<RepaymentOrderContract.RepaymentOrderView> implements RepaymentOrderContract.Presenter<RepaymentOrderContract.RepaymentOrderView> {
    ServiceManager mServiceManager;

    @Inject
    public RepaymentOrderPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestRepaymentOrderList(int id) {
        mServiceManager.getUserService()
                .requestRepaymentOrder(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<RepaymentOrder>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<RepaymentOrder> repaymentOrderPage) {
                        mView.onContent();
                        mView.setRepaymentOrder(repaymentOrderPage.data);
                    }
                });
    }
}
