package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.ui.user.contract.JointLoginContract;

import javax.inject.Inject;

/**
 * Created by WORK on 2017/4/28.
 */

public class JointLoginPresenter extends RxPresenter<JointLoginContract.JointLoginView> implements JointLoginContract.Presenter<JointLoginContract.JointLoginView> {
    ServiceManager mServiceManager;

    @Inject
    public JointLoginPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
