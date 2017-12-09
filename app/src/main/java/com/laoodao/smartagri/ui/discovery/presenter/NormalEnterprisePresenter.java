package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Menu;
import com.laoodao.smartagri.ui.discovery.contract.NormalEnterpriseContract;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/6/28.
 */

public class NormalEnterprisePresenter extends RxPresenter<NormalEnterpriseContract.NormalEnterpriseView> implements NormalEnterpriseContract.Presenter<NormalEnterpriseContract.NormalEnterpriseView> {


    ServiceManager mServiceManager;

    @Inject
    public NormalEnterprisePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}