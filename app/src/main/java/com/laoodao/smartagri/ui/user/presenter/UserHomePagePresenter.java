package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.bean.UserHomePage;
import com.laoodao.smartagri.bean.UserHomePageData;
import com.laoodao.smartagri.bean.UserHomePageHead;

import com.laoodao.smartagri.bean.base.Response;

import com.laoodao.smartagri.bean.base.Page;

import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.UserHomePageContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/8/14.
 */

public class UserHomePagePresenter extends ListPresenter<UserHomePageContract.UserHomePageView> implements UserHomePageContract.Presenter<UserHomePageContract.UserHomePageView> {
    ServiceManager mServiceManager;

    @Inject
    public UserHomePagePresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void follow(int id, int position) {
        mServiceManager.getQAService()
                .addWonder(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> reuslt) {
                        mView.followSuccess(position, reuslt.data);
                    }
                });
    }

    @Override
    public void unfollow(int questionId, int position) {
        mServiceManager.getQAService()
                .delWonder(questionId)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> reuslt) {
                        mView.unFollowSuccess(position, reuslt.data);
                    }
                });
    }

    @Override
    public void share(String tag, int id) {
        mServiceManager.getHomeService()
                .shareBack(tag, id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<String> response) {

                    }
                });
    }

    @Override
    public void rquestHeaderData(int id) {
        mServiceManager.getUserService()
                .userHomePageHeader(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<UserHomePageHead>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<UserHomePageHead> userHomePageHeaderResult) {
                        mView.setUserHomePageHeader(userHomePageHeaderResult.data);
                    }
                });
    }


    @Override
    public void addWonder(int id) {
        mServiceManager.getUserService()
                .addWonfer(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        mView.addWonderSuccess();
                    }
                });
    }


    @Override
    public void userHomePageData(int page, int id, int type) {
        mServiceManager.getUserService()
                .userHomePageData(page, id, type)
                .compose(transform())
                .subscribe(new Subscriber<Page<UserHomePageData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<UserHomePageData> userHomePageDataPage) {
                        onPageLoaded(userHomePageDataPage);
                        mView.onContent();
                        mView.setListSize(userHomePageDataPage.data.total);
                    }
                });
    }


}
