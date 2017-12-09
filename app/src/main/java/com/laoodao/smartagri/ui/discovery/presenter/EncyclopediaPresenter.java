package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.EncyclopediaContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/4/20.
 */

public class EncyclopediaPresenter extends ListPresenter<EncyclopediaContract.EncyclopediaView> implements EncyclopediaContract.Presenter<EncyclopediaContract.EncyclopediaView> {


    ServiceManager mServiceManager;

    @Inject
    public EncyclopediaPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestCateList() {
        mServiceManager.getDiscoverService()
                .baikeCateList()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<CateList>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<CateList> result) {
                        requestShowMenu(1);
                        if (result != null) {
                            if (result.data.categoryList != null) {
                                result.data.categoryList.get(0).isSelect = true;
                            }
                        }
                        mView.showCateList(result.data);
                    }
                });
    }

    @Override
    public void requestShowMenu(int type) {
        mServiceManager.getDiscoverService()
                .getBaikeMenu(type)
                .compose(transform())
                .subscribe(new Subscriber<Result<BaikeMenu>>() {
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
                    public void onNext(Result<BaikeMenu> menuResult) {
                        if (menuResult == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.showMenu(menuResult.data);
                    }
                });
    }
}
