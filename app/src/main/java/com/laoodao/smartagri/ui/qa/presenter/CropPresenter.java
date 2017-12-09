package com.laoodao.smartagri.ui.qa.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Crop;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.qa.contract.CropContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by 欧源 on 2017/4/29.
 */

public class CropPresenter extends RxPresenter<CropContract.CropView> implements CropContract.Presenter<CropContract.CropView> {


    ServiceManager mServiceManager;

    @Inject
    public CropPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getCropList() {
        mServiceManager.getQAService()
                .getCropList()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Crop>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Crop>> result) {
                        mView.showCropList(result.data);
                    }
                });
    }

    @Override
    public void getSearchCropList(int page, String keyWord) {
        mServiceManager.getQAService()
                .searchPlant(page, keyWord)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<Plant>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<Plant> plantPage) {
                        mView.searchCropList(plantPage.data.items);
                    }
                });
    }
}
