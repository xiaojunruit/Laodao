package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.market.contact.MarketSearchContract;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/3.
 */

public class MarketSearchPresenter extends ListPresenter<MarketSearchContract.MarketSearchView> implements MarketSearchContract.Presenter<MarketSearchContract.MarketSearchView> {
    ServiceManager mServiceManager;

    @Inject
    public MarketSearchPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void searchSupply(int page, String keyword) {
        mServiceManager.getMarketService()
                .getSupplylists(page, keyword)
                .compose(transform())
                .subscribe(new Subscriber<Page<Supplylists>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Page<Supplylists> supplylistsPage) {
                        mView.showSupplyList(supplylistsPage.data.items, supplylistsPage.data.page < 2);
                        if (supplylistsPage.data.total == 0) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        boolean noMore = supplylistsPage.data.page * supplylistsPage.data.size >= supplylistsPage.data.total;
                        mView.noMore(noMore);
                    }
                });
    }

    @Override
    public void requestAddSearch(String keywords) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("keywords", keywords);
        mServiceManager.getMarketService()
                .addSearch(builder.build())
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.Search();
                    }
                });
    }

    @Override
    public void getHostWords() {
        mServiceManager.getMarketService()
                .getHostWords()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Keyword>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<List<Keyword>> listResult) {
                        mView.showHostWords(listResult.data);
                    }
                });
    }

    @Override
    public void getMySearch() {
        mServiceManager.getMarketService()
                .getMySearchWords()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Keyword>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Keyword>> listResult) {
                        mView.mySearch(listResult.data);
                    }
                });

    }

    @Override
    public void requestDelHistory(int id) {
        mServiceManager.getMarketService()
                .delSearch(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.deleteHistory();
                    }
                });
    }
}
