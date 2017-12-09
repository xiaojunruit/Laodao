package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.ReturnGoods;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.user.contract.ReturnGoodsContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/7/5.
 */

public class ReturnGoodsPresenter extends ListPresenter<ReturnGoodsContract.ReturnGoodsView> implements ReturnGoodsContract.Presenter<ReturnGoodsContract.ReturnGoodsView> {
    ServiceManager mServiceManager;

    @Inject
    public ReturnGoodsPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void returnGoodsList(int page) {
        mServiceManager.getUserService()
                .returnGoodsList(page)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }
}
