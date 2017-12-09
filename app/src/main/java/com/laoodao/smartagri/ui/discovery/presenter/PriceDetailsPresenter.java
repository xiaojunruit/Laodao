package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.CottonfieldComment;
import com.laoodao.smartagri.bean.CottonfieldDetail;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.PriceDetailsContract;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class PriceDetailsPresenter extends ListPresenter<PriceDetailsContract.PriceDetailsView> implements PriceDetailsContract.Presenter<PriceDetailsContract.PriceDetailsView> {


    ServiceManager mServiceManager;

    @Inject
    public PriceDetailsPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList(int id) {
        mServiceManager.getDiscoverService()
                .priceDetail(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<CottonfieldDetail>>() {
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
                    public void onNext(Result<CottonfieldDetail> result) {
                        if (result.data != null) {
                            mView.onContent();
                            mView.successList(result.data);
                        } else {
                            mView.onEmpty();
                        }
                    }
                });
    }

    @Override
    public void CommentList(int id, int page) {
        mServiceManager.getDiscoverService()
                .getComment(id, page)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<CottonfieldComment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Page<CottonfieldComment> commentPage) {
                        boolean noMore = commentPage.data.size * commentPage.data.page >= commentPage.data.total;
                        mView.noMore(noMore);
                        mView.successCommentList(commentPage.data.items, commentPage.data.page < 2);
                    }
                });

    }

    @Override
    public void addComment(int id, String content) {
        mServiceManager.getDiscoverService()
                .addComment(id, content)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<CottonfieldComment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<CottonfieldComment> result) {
                        mView.successAddComment(result.data);
                    }
                });
    }

    @Override
    public void requestFollow(int id) {
        mServiceManager.getDiscoverService()
                .addCottonFieldWonder(id)
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
                        mView.successFollow();
                    }
                });

    }

    @Override
    public void shareBack(String tag, int id) {
        mServiceManager.getHomeService()
                .shareBack(tag, id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<String> stringResult) {

                    }
                });
    }
}