package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.discovery.contract.MapReportingErrorsContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/8/9.
 */

public class MapReportingErrorsPresenter extends RxPresenter<MapReportingErrorsContract.MapReportingErrorsView> implements MapReportingErrorsContract.Presenter<MapReportingErrorsContract.MapReportingErrorsView> {
    ServiceManager mServiceManager;

    @Inject
    public MapReportingErrorsPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
