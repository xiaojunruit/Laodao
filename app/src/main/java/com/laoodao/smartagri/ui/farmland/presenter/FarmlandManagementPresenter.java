package com.laoodao.smartagri.ui.farmland.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.AccountList;
import com.laoodao.smartagri.bean.LedgerStatistics;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.farmland.contract.FarmlandManagementContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 */

public class FarmlandManagementPresenter extends RxPresenter<FarmlandManagementContract.FarmlandManagementView> implements FarmlandManagementContract.Presenter<FarmlandManagementContract.FarmlandManagementView> {
    ServiceManager mServiceManager;

    @Inject
    public FarmlandManagementPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestStatistics() {
        mServiceManager.getFarmlandService()
                .getLedgerStatistics()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<LedgerStatistics>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<LedgerStatistics> ledgerStatisticsResult) {
                        mView.initStatistics(ledgerStatisticsResult.data);
                    }
                });

    }

    @Override
    public void requestAccountList(int page) {
        mServiceManager.getFarmlandService()
                .getAccountList(page)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<AccountList>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Page<AccountList> accountListPage) {
                        boolean noMore = accountListPage.data.page * accountListPage.data.size >= accountListPage.data.total;
                        mView.noMore(noMore);
                        boolean isRefresh = accountListPage.data.page < 2;
                        mView.initAccountList(accountListPage, isRefresh);
                    }
                });
    }
}
