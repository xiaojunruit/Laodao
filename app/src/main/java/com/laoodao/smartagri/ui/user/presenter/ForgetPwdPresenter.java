package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.ForgetPwdContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class ForgetPwdPresenter extends RxPresenter<ForgetPwdContract.ForgetPwdView> implements ForgetPwdContract.Presenter<ForgetPwdContract.ForgetPwdView> {

    ServiceManager mServiceManager;
    @Inject
    public ForgetPwdPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }




}
