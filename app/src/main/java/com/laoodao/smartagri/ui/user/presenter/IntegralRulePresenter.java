package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.IntegralRule;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.IntegralRuleContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/10.
 */

public class IntegralRulePresenter extends RxPresenter<IntegralRuleContract.IntegralRuleView> implements IntegralRuleContract.Presenter<IntegralRuleContract.IntegralRuleView>{
    ServiceManager mServiceManager;
    @Inject
    public IntegralRulePresenter(ServiceManager serviceManager){
        this.mServiceManager=serviceManager;
    }

    @Override
    public void requestIntegralRule() {
        mServiceManager.getUserService()
                .pointsRule()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<IntegralRule>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<IntegralRule>> listResult) {
                        mView.integralRule(listResult.data);
                    }
                });
    }
}
