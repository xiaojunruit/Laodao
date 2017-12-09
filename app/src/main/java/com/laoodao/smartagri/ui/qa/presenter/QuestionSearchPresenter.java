package com.laoodao.smartagri.ui.qa.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.qa.contract.QuestionSearchContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/5/2.
 */

public class QuestionSearchPresenter extends RxPresenter<QuestionSearchContract.QuestionSearchView> implements QuestionSearchContract.Presenter<QuestionSearchContract.QuestionSearchView> {


    ServiceManager mServiceManager;

    @Inject
    public QuestionSearchPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getHotKeyword() {
        mServiceManager.getQAService()
                .getHotKeyword()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Keyword>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Keyword>> result) {
                        mView.showHotKeyword(result.data);
                    }
                });
    }

    @Override
    public void getHistorySearch() {
        mServiceManager.getQAService()
                .getHistorySearch()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Keyword>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Keyword>> result) {
                        mView.showHistory(result.data);
                    }
                });
    }

    @Override
    public void searchQuestion(int page, String keyword) {
        mServiceManager.getQAService()
                .getQuestList(page, keyword)
                .compose(Api.checkOn(mView))
                .doAfterTerminate(() -> mView.complete())
                .subscribe(new Subscriber<Page<Question>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Page<Question> result) {
                        mView.showQuestionList(result.data.items, result.data.page < 2);
                        if (result.data.total == 0) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        boolean noMore = result.data.page * result.data.size >= result.data.total;
                        mView.noMore(noMore);
                    }
                });
    }

    @Override
    public void deleteHistory(int id) {
        mServiceManager.getQAService()
                .deleteHistory(id)
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
                        mView.delteSuccess();
                    }
                });
    }

    @Override
    public void addSearch(String keyword) {
        mServiceManager.getQAService()
                .addSearch(keyword)
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
                        mView.addSearchSuccess();
                    }
                });
    }
}
