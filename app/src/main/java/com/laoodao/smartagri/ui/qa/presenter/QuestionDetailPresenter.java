package com.laoodao.smartagri.ui.qa.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.ReplySuccess;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.market.contact.MySupplyDemandContact;
import com.laoodao.smartagri.ui.qa.contract.QuestionDetailContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/4/17.
 */

public class QuestionDetailPresenter extends ListPresenter<QuestionDetailContract.QuestionDetailView> implements QuestionDetailContract.Presenter<QuestionDetailContract.QuestionDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public QuestionDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getQuestionDetail(int id) {
        mServiceManager.getQAService()
                .getQuestionDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<Question>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<Question> qaResult) {
                        if (qaResult.data == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.showQuestionDetail(qaResult.data);
                    }
                });
    }

    @Override
    public void getAnswerList(int askId, int page) {
        mServiceManager.getQAService()
                .getAnswerList(askId, page)
                .compose(Api.checkOn(mView))
                .doAfterTerminate(() -> mView.complete())
                .subscribe(new Subscriber<Page<Answer>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Page<Answer> result) {
                        boolean noMore = result.data.size * result.data.page >= result.data.total;
                        mView.noMore(noMore);
                        mView.showAnswerList(result.data.items, result.data.page < 2);
                    }
                });
    }

    @Override
    public void unfollow(int questionId) {
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
                    public void onNext(Result<Map<String, String>> result) {
                        mView.followChange(result.data);
                    }
                });
    }

    @Override
    public void follow(int questionId) {

        mServiceManager.getQAService()
                .addWonder(questionId)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> result) {
                        mView.followChange(result.data);
                    }
                });


    }

    @Override
    public void reply(int id, String reply, int position) {
        mServiceManager.getQAService()
                .reply(id, reply)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<ReplySuccess>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<ReplySuccess> result) {
                        mView.replySuccess(result.data, position);
                    }
                });
    }

    @Override
    public void praise(int position, int answerId) {
        mServiceManager.getQAService()
                .praise(answerId)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> result) {
                        mView.praiseSuccess(position, result.data);
                    }
                });

    }

    @Override
    public void answer(int type_answer, int mQuestionId, String reply) {
        mServiceManager.getQAService()
                .answer(type_answer, mQuestionId, reply)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Answer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Answer> result) {
                        mView.answerSuccess(result.data);
                    }
                });
    }

    @Override
    public void collect(int mQuestionId) {
        mServiceManager.getQAService()
                .collect(mQuestionId)
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
                        mView.collectSuccess();
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

    @Override
    public void addUserFollow(int id, int position) {
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
                        mView.UserFollowSuccess(position);
                    }
                });
    }
}
