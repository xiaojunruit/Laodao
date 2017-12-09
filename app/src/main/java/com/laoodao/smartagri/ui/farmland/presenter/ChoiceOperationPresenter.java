package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.farmland.contract.ChoiceOperationContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/25.
 */

public class ChoiceOperationPresenter extends RxPresenter<ChoiceOperationContract.ChoiceOperationView> implements ChoiceOperationContract.Presenter<ChoiceOperationContract.ChoiceOperationView> {
    ServiceManager mServiceManager;

    @Inject
    public ChoiceOperationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestChoiceOperation() {
        mServiceManager.getFarmlandService()
                .getClassification(Constant.EXPENDITURE)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<RecordClassification>>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<List<RecordClassification>> listResult) {
                        mView.choiceOperationSuccess(listResult.data);
                    }
                });
    }
}
