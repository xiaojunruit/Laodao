package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.ui.discovery.contract.NewsSearchContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/20.
 */

public class NewsSearchPresenter extends ListPresenter<NewsSearchContract.NewsSearchView> implements NewsSearchContract.Presenter<NewsSearchContract.NewsSearchView> {
    ServiceManager mServiceManager;

    @Inject
    public NewsSearchPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestNewsSearch(int gcId, int page, String kw) {
        mServiceManager.getDiscoverService()
                .getNewsList(page, gcId, kw)
                .compose(transform())
                .subscribe(new Subscriber<Page<News>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<News> newsPage) {
                        onPageLoaded(newsPage);
                        mView.newsSearchSuccess();
                    }
                });
    }


//    new Subscriber<Page<News>>() {
//        @Override
//        public void onCompleted() {
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
//
//        }
//
//        @Override
//        public void onNext(Page<News> newsPage) {
//            if (newsPage.data.page * newsPage.data.size >= newsPage.data.total) {
//                mView.noMore();
//            }
//            mView.newsSearchSuccess(newsPage.data.items, newsPage.data.page < 2);
//        }
//    }
}
