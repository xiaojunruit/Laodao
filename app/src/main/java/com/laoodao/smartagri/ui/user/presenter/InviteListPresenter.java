package com.laoodao.smartagri.ui.user.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.Invite;
import com.laoodao.smartagri.bean.InviteList;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.InviteListContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class InviteListPresenter extends ListPresenter<InviteListContract.IniteListView> implements InviteListContract.Presenter<InviteListContract.IniteListView> {
    ServiceManager mServiceManager;

    @Inject
    public InviteListPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestInviteListData(int page) {

        Observable<Page<InviteList>> fromWork;
        Observable<Page<InviteList>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.LAOODAO_SHARE_LIST + page;
            fromWork = mServiceManager.getUserService().inviteList(page).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.rxCreateDiskObservable(key, new TypeToken<Page<InviteList>>() {
            }.getType());
        } else {
            fromWork = mServiceManager.getUserService().inviteList(page).compose(transform());
        }
        Observable.concat(observable, fromWork)
                .compose(transform())
                .subscribe(this::onPageLoaded);
//        mServiceManager.getUserService()
//                .inviteList(page)
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Page<InviteList>>() {
//                    @Override
//                    public void onCompleted() {
//                        mView.complete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("onError->" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Page<InviteList> inviteListPage) {
//                        if (inviteListPage.data.page * inviteListPage.data.size >= inviteListPage.data.total) {
//                            mView.noMore();
//                        }
//                        mView.inviteListSuccess(inviteListPage.data.items, inviteListPage.data.page < 2);
//                    }
//                });
    }
}
