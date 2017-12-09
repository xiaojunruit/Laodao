package com.laoodao.smartagri.ui.discovery.presenter;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.discovery.contract.PriceQuotationContract;
import com.laoodao.smartagri.ui.discovery.contract.PriceQuotationSearchContract;
import com.laoodao.smartagri.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/8/1.
 */

public class PriceQuotationSearchPresenter extends ListPresenter<PriceQuotationSearchContract.PriceQuotationSearchView> implements PriceQuotationSearchContract.Presenter<PriceQuotationSearchContract.PriceQuotationSearchView> {
    ServiceManager mServiceManager;

    @Inject
    public PriceQuotationSearchPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getHotKeyword() {
        mServiceManager.getDiscoverService()
                .hotKeyword()
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
                        mView.showHotKeyword(listResult.data);
                    }
                });
    }

    @Override
    public void requestData(int cateId, int page, int timeType, String keyword, String pos, int areaId) {
        mServiceManager.getDiscoverService()
                .getPriceQuotation(cateId, page, timeType, keyword, pos, areaId)
                .compose(transform())
                .subscribe(new Subscriber<Page<PriceQuotation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Page<PriceQuotation> priceQuotationPage) {
                        onPageLoaded(priceQuotationPage);
                        mView.sucess();
                    }
                });
    }

}
