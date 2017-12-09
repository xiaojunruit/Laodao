package com.laoodao.smartagri.ui.discovery.presenter;

import com.google.gson.annotations.SerializedName;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.NewDrug;
import com.laoodao.smartagri.bean.NewDrugTab;
import com.laoodao.smartagri.bean.Unipue;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.discovery.contract.CropDetailContract;
import com.laoodao.smartagri.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/17.
 */

public class CropDetailPresenter extends ListPresenter<CropDetailContract.CropDetailView> implements CropDetailContract.Presenter<CropDetailContract.CropDetailView> {
    ServiceManager mServiceManager;

    @Inject
    public CropDetailPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    public void requestNewDrugTab(int id) {
        mServiceManager.getDiscoverService()
                .getNewDrugTab(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<NewDrugTab>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<NewDrugTab> newDrugTabResult) {
                        if (newDrugTabResult == null) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }
                        mView.newDrugTab(newDrugTabResult.data);
                    }
                });
    }

    @Override
    public void requestNewDrugList(int id) {
//        List<NewDrug> newDrugs = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            NewDrug newDrug;
//            List<NewDrug.TopCenter> child = new ArrayList<>();
//            for (int i1 = 0; i1 < 2; i1++) {
//                NewDrug.TopCenter a=new NewDrug.TopCenter();
//                a.subtitle="aaaaaaaaaaaa";
//                a.content="assafzxcvasdfsdasf";
//                child.add(a);
//            }
//            newDrug=new NewDrug(child);
//            newDrug.topTitle="askfdjslfjsjf";
//            newDrugs.add(newDrug);
//        }
//        mView.newDrugListSuccess(newDrugs);

        mServiceManager.getDiscoverService()
                .getNewDrug(id, Constant.NEW_DRUG)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<NewDrug>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<NewDrug>> newDrugPage) {
                        mView.newDrugListSuccess(newDrugPage.data);
                    }
                });
    }

    @Override
    public void requestUnipueList(int id) {
        mServiceManager.getDiscoverService()
                .getUnipue(id, Constant.UNIPUE)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Page<Unipue>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Page<Unipue> unipuePage) {
                        mView.unipueSuccess(unipuePage.data.items);
                    }
                });
    }

    @Override
    public void requestDrugCollect(int id) {
        mServiceManager.getDiscoverService()
                .drugCollect(id)
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
                        mView.drugCollectSuccess();
                    }
                });
    }
}
