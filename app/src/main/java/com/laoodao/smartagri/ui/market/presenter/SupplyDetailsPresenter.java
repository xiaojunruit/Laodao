package com.laoodao.smartagri.ui.market.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.ChatGoodsInit;
import com.laoodao.smartagri.bean.SupplyDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.market.contact.SupplyDetailsContact;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SupplyDetailsPresenter extends ListPresenter<SupplyDetailsContact.SupplyDetailsView> implements SupplyDetailsContact.Presenter<SupplyDetailsContact.SupplyDetailsView> {
    ServiceManager mServiceManager;

    @Inject
    public SupplyDetailsPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestSupplyDetail(int id) {
        mServiceManager.getMarketService()
                .getSupplyDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<SupplyDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<SupplyDetail> supplyDetailResult) {
                        if (supplyDetailResult.data == null) {
                            mView.onEmpty();
                        } else {
                            mView.initSupplyDetail(supplyDetailResult.data);
                            mView.onContent();
                        }
                    }
                });
    }

    @Override
    public void requestAdd(int itemId) {
        mServiceManager.getMarketService()
                .addCollect(itemId)
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
                        mView.addCollection();
                    }
                });
    }

    @Override
    public void requestDel(int itemId) {
        mServiceManager.getMarketService()
                .delCollect(itemId)
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
                        mView.delCollection();
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

    @Override
    public void addTellog(int id) {
        mServiceManager.getMarketService()
                .addTellog(id)
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

                    }
                });
    }

    @Override
    public void chatInit(String id, String tag) {
        mServiceManager.getMarketService()
                .getChatGoodsInit(id, tag)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<ChatGoodsInit>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<ChatGoodsInit> chatGoodsInitResult) {
                        mView.setChatGoodsInit(chatGoodsInitResult.data);
                    }
                });
    }


}
