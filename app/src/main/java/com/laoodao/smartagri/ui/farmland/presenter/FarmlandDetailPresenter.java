package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.FarmlandDetail;
import com.laoodao.smartagri.bean.FarmlandDetailInfo;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.farmland.contract.FarmlandDetailContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/24.
 */

public class FarmlandDetailPresenter extends RxPresenter<FarmlandDetailContract.FarmlandDetailView> implements FarmlandDetailContract.Presenter<FarmlandDetailContract.FarmlandDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public FarmlandDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestFarmlandDetail(int id) {
        mServiceManager.getFarmlandService()
                .farmlandDetail(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<FarmlandDetailInfo>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<FarmlandDetailInfo> result) {
                        mView.requestFarmlandDetailSuccess(result.data);
                    }
                });
    }

    @Override
    public void requestFarmlandDetailList(int page, int id) {
        mServiceManager.getFarmlandService()
                .farmlandDetailList(page, id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<FarmlandDetail>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Page<FarmlandDetail> result) {
                        boolean noMore = result.data.page*result.data.size>=result.data.total;
                            mView.noMore(noMore);

                        mView.requestFarmlandDetailListSuccess(result.data.items, result.data.page < 2);
                    }
                });
    }
}
