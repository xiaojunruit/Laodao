package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Farmland;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Pagination;
import com.laoodao.smartagri.ui.farmland.contract.MyFarmlandContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/23.
 */

public class MyFarmlandPresenter extends RxPresenter<MyFarmlandContract.MyFarmlandView> implements MyFarmlandContract.Presenter<MyFarmlandContract.MyFarmlandView> {
    ServiceManager mServiceManager;

    @Inject
    public MyFarmlandPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getFarmlandList(int page) {
        mServiceManager.getFarmlandService()
                .getFarmlandList(page)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<Farmland>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Page<Farmland> result) {
                        boolean noMore = result.data.page * result.data.size >= result.data.total;
                        mView.noMore(noMore);
                        Pagination<Farmland> data = result.data;
                        boolean isRefresh = data.page < 2;
                        mView.showFarmlandList(result.data, isRefresh);

                    }
                });
    }
}
