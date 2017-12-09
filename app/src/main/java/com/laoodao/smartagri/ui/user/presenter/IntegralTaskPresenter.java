package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.IntegralTask;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.IntegralTaskContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/4/26.
 */

public class IntegralTaskPresenter extends RxPresenter<IntegralTaskContract.IntegralTaskView> implements IntegralTaskContract.Presenter<IntegralTaskContract.IntegralTaskView> {


    ServiceManager mServiceManager;

    @Inject
    public IntegralTaskPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void getTaskList() {
        mServiceManager.getUserService()
                .earnPoints()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<IntegralTask>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->"+e.getMessage());
                    }

                    @Override
                    public void onNext(Result<List<IntegralTask>> listResult) {
                        mView.showTaskList(listResult.data);
                    }
                });
    }
}
