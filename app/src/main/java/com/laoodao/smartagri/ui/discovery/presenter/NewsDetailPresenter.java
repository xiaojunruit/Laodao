package com.laoodao.smartagri.ui.discovery.presenter;

import android.text.TextUtils;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.NewsDetail;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.NewsDetailContract;
import com.laoodao.smartagri.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/4/22.
 */

public class NewsDetailPresenter extends ListPresenter<NewsDetailContract.NewsDetailView> implements NewsDetailContract.Presenter<NewsDetailContract.NewsDetailView> {


    ServiceManager mServiceManager;

    @Inject
    public NewsDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void requestNewsDetail(int id) {
        mServiceManager.getDiscoverService()
                .getNewsDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<NewsDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Result<NewsDetail> newsDetailResult) {
                        if (TextUtils.isEmpty(newsDetailResult.data.title)) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.newsDetailSuccess(newsDetailResult.data);
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
