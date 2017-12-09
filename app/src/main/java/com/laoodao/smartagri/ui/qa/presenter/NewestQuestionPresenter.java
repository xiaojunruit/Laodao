package com.laoodao.smartagri.ui.qa.presenter;

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
import com.laoodao.smartagri.ui.qa.contract.NewestQuestionContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by WORK on 2017/4/15.
 */

public class NewestQuestionPresenter extends ListPresenter<NewestQuestionContract.NewestQuestionView> implements NewestQuestionContract.Presenter<NewestQuestionContract.NewestQuestionView> {
    ServiceManager mServiceManager;

    @Inject
    public NewestQuestionPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getNewsQuestionList(int page) {
        Observable<Page<Question>> fromNetWork;
        Observable<Page<Question>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.NEW_QUESTION_LIST_ + page;
            fromNetWork = mServiceManager.getQAService().getQuestList(page, null).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.<Page<News>>rxCreateDiskObservable(key, new TypeToken<Page<Question>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getQAService().getQuestList(page, null).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);

//        mServiceManager.getQAService()
//                .getQuestList(page, null)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);

//        mServiceManager.getQAService()
//                .getQuestList(page, null)
//                .compose(transform())
//                .subscribe(this::onPageLoaded);
//
//        Observable<Page<Question>> fromNetWork;
//        Observable<Page<Question>> observable = Observable.empty();
//        if (page==1) {
//            String key = "neweest_question_list_page_" + page;
//            fromNetWork = mServiceManager.getQAService().getQuestList(page, null).compose(RxUtils.rxCacheListHelper(key));
//            observable = RxUtils.rxCreateDiskObservable(key, new TypeToken<Page<News>>() {
//            }.getType());
//        }else{
//
//        }


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
                        mView.shareSuccess();
                    }
                });
    }

}
