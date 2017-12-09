package com.laoodao.smartagri.ui.qa.presenter;

import android.os.Handler;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.qa.contract.FollowContentContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/17.
 */

public class FollowContentPresenter extends ListPresenter<FollowContentContract.FollowContentView> implements FollowContentContract.Presenter<FollowContentContract.FollowContentView> {
    ServiceManager mServiceManager;

    @Inject
    public FollowContentPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void getFollowContent(String type, int page) {
        Observable<Page<Question>> fromNetWork;
        Observable<Page<Question>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.NEW_FOLLOW_CONTENT_ + page;
            fromNetWork = mServiceManager.getQAService().getQAByType(type, page).compose(RxUtils.<Page<Question>>rxCacheListHelper(key));
            observable = RxUtils.<Page<Question>>rxCreateDiskObservable(key, new TypeToken<Page<Question>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getQAService().getQAByType(type, page).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);
//        mServiceManager.getQAService()
//                .getQAByType(type, page)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
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
                        mView.followSuccess(position, reuslt.data);
                    }
                });
    }

    @Override
    public void followPlant(String ids) {
        mServiceManager.getQAService()
                .followPlant(ids)
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
                        mView.followPlantSuccess();
                    }
                });
    }

    @Override
    public void share(String tag,int id) {
        mServiceManager.getHomeService()
                .shareBack(tag,id)
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
                        mView.shareSuccess();
                    }
                });
    }
}
