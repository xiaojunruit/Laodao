package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.HistoryRecord;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.HistoryRecordContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/6.
 */

public class HistoryRecordPresenter extends ListPresenter<HistoryRecordContract.HistoryRecordView> implements HistoryRecordContract.Presenter<HistoryRecordContract.HistoryRecordView> {
    ServiceManager mServiceManger;

    @Inject
    public HistoryRecordPresenter(ServiceManager serviceManager) {
        this.mServiceManger = serviceManager;
    }

    @Override
    public void requestHistoryRecord(int id) {
        mServiceManger.getUserService()
                .historyRecord(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<HistoryRecord>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<HistoryRecord> historyRecordResult) {
                        mView.onContent();
                        mView.historyRecordSuccess(historyRecordResult.data);
                    }
                });
    }
}
