package com.laoodao.smartagri.ui.home.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.cache.CacheManager;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.home.contract.HomeContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

import static com.laoodao.smartagri.utils.RxUtils.rxCreateDiskObservable;

/**
 * Created by Administrator on 2017/4/11.
 */

public class HomePresenter extends ListPresenter<HomeContract.HomeView> implements HomeContract.Presenter<HomeContract.HomeView> {

    ServiceManager mServiceManager;

    CacheManager mCacheManager;

    @Inject
    public HomePresenter(ServiceManager serviceManager, CacheManager cacheManager) {
        this.mServiceManager = serviceManager;
        this.mCacheManager = cacheManager;
    }







    @Override
    public void requestHomeNews(int page) {
        Observable<Page<News>> fromNetWork;
        Observable<Page<News>> observable = Observable.empty();
        if (page == 1) {
            String key = Constant.HOME_NEWS_LIST_PAGE_ + page;
            fromNetWork = mServiceManager.getHomeService().homeNees(page).compose(RxUtils.rxCacheListHelper(key));
            observable = RxUtils.<Page<News>>rxCreateDiskObservable(key, new TypeToken<Page<News>>() {
            }.getType());
        } else {
            fromNetWork = mServiceManager.getHomeService().homeNees(page).compose(transform());
        }
        Observable.concat(observable, fromNetWork).compose(transform()).subscribe(this::onPageLoaded);


        //  String key = "home_news_list_page_" + page;

//        Observable<Page<News>> observable = mServiceManager.getHomeService().homeNees(page);
//
//        Observable<Page<News>> cacheObservable = mCacheManager.getHomeCache().homeNees(observable, new EvictProvider(false))
//                .flatMap(result -> Observable.just(result.getData()));
//
//        cacheObservable.compose(transform())
//                .subscribe(new Subscriber<Page<News>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("=================>>>" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Page<News> newsPage) {
//
//                        String toJson = new Gson().toJson(newsPage);
//                        Page<News> newsPageData = new Gson().fromJson(toJson, new TypeToken<Page<News>>() {
//                        }.getType());
//                        onPageLoaded(newsPageData);
//                    }
//                });
    }

    @Override
    public void requestHome(String cityId, String pos) {


//        Observable<Result<Home>> observable = mServiceManager.getHomeService().homeMenu(cityId, pos);
//
//        Observable<Result<Home>> resultObservable = mCacheManager.getHomeCache().getHomeMenu(observable, new EvictProvider(false))
//                .flatMap(result -> Observable.just(result.getData()));
//
//
//        resultObservable
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<Home>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        LogUtil.e("============>>>" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Result<Home> homeResult) {
//
//                        String toJson = new Gson().toJson(homeResult);
//                        Result<Home> homeData = new Gson().fromJson(toJson, new TypeToken<Result<Home>>() {
//                        }.getType());
//                        mView.initHome(homeData.data);
//                    }
//                });


        String key = "home_menu";
        Observable<Result<Home>> fromNetWork = mServiceManager.getHomeService().homeMenu(cityId, pos).compose(RxUtils.rxCacheListHelper(key));
        Observable.concat(RxUtils.<Result<Home>>rxCreateDiskObservable(key, new TypeToken<Result<Home>>() {
        }.getType()), fromNetWork)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Home>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(Result<Home> homeResult) {
                        mView.initHome(homeResult.data);
                    }
                });
    }
}
