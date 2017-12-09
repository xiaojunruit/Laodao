package com.laoodao.smartagri.ui.market.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.SupplyMy;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.market.contact.ReleaseSupplyContact;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ReleaseSupplyPresenter extends ListPresenter<ReleaseSupplyContact.DistributionView> implements ReleaseSupplyContact.Presenter<ReleaseSupplyContact.DistributionView> {
    ServiceManager mServiceManager;

    @Inject
    public ReleaseSupplyPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDate(int page, int type) {
        Observable<Page<SupplyMy>> fromNetWork;
        Observable<Page<SupplyMy>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.RELEASE_SUPPLY + page;
            fromNetWork = mServiceManager.getMarketService()
                    .getSupplyMy(page, type).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.<Page<SupplyMy>>rxCreateDiskObservable(key, new TypeToken<Page<SupplyMy>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getMarketService()
                    .getSupplyMy(page, type).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);
//        mServiceManager.getMarketService()
//                .getSupplyMy(page, type)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);

    }

    @Override
    public void requestDel(int id) {
        mServiceManager.getMarketService()
                .delSupply(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.deleteData();
                    }
                });
    }
}
