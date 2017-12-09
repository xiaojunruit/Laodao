package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.ReturnGoodsDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.ReturnGoodsContract;
import com.laoodao.smartagri.ui.user.contract.ReturnGoodsDetailContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/7/6.
 */

public class ReturnGoodsDetailPresenter extends ListPresenter<ReturnGoodsDetailContract.ReturnGoodsDetailView> implements ReturnGoodsDetailContract.Presenter<ReturnGoodsDetailContract.ReturnGoodsDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public ReturnGoodsDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestReturnGoodsDetail(int id) {
        mServiceManager.getUserService()
                .requestReturnGoodsDetail(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<ReturnGoodsDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<ReturnGoodsDetail> returnGoodsDetailResult) {
                        mView.onContent();
                        mView.setReturnGoodsDetail(returnGoodsDetailResult.data);

                    }
                });
    }
}
