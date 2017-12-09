package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.MenuMoreContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/6/21.
 */

public class MenuMorePresenter extends ListPresenter<MenuMoreContract.MenuMoreView> implements MenuMoreContract.Presenter<MenuMoreContract.MenuMoreView> {


    ServiceManager mServiceManager;

    @Inject
    public MenuMorePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void allMenuRequest(int type) {
        mServiceManager.getDiscoverService()
                .discoverMenu(type)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Discovercat>>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<List<Discovercat>> listResult) {
                        if (listResult == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.allMenu(listResult.data);
                    }
                });
    }
}