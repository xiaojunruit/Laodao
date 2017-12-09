package com.laoodao.smartagri.ui.discovery.presenter;

import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.bean.CropCategory;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.discovery.contract.PriceQuotationContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.internal.util.unsafe.MpmcArrayQueue;

/**
 * Created by WORK on 2017/7/28.
 */

public class PriceQuotationPresenter extends ListPresenter<PriceQuotationContract.PriceQuotationView> implements PriceQuotationContract.Presenter<PriceQuotationContract.PriceQuotationView> {
    ServiceManager mServiceManager;

    @Inject
    public PriceQuotationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestData(int cateId, int page, int timeType, String keyword, String pos, int areaId) {
        mServiceManager.getDiscoverService()
                .getPriceQuotation(cateId, page, timeType, keyword, pos, areaId)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }

    @Override
    public void requestDateType() {
        mServiceManager.getDiscoverService()
                .priceDate()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<List<SupplyMenu>> selectionItemResult) {
                        mView.setDateMenuList(selectionItemResult.data);
                    }
                });
    }

    @Override
    public void requestCropType() {
        mServiceManager.getDiscoverService()
                .cropCategory()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<SupplyMenu>> supplyMenuResult) {
                        mView.setCropMenuList(supplyMenuResult.data);
                    }
                });
    }

    @Override
    public void addCottonFieldWonder(int id) {
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

                    }
                });
    }

    @Override
    public void isMessage() {
        mServiceManager.getDiscoverService()
                .isNewMessage()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        mView.setMessage((boolean) result.data);
                    }
                });
    }


}
