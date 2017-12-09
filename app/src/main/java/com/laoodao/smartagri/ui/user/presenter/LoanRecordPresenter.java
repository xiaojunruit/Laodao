package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.user.contract.LoanRecordContract;

import javax.inject.Inject;

/**
 * Created by Long-PC on 2017/4/14.
 */

public class LoanRecordPresenter extends RxPresenter<LoanRecordContract.LoanRecordView> implements LoanRecordContract.Presenter<LoanRecordContract.LoanRecordView>{

    ServiceManager mServiceManager;
    @Inject
    public LoanRecordPresenter(ServiceManager serviceManager){
        this.mServiceManager=serviceManager;
    }
}
