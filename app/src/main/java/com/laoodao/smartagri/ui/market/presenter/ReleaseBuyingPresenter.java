package com.laoodao.smartagri.ui.market.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.market.contact.ReleaseBuyingContact;
import com.laoodao.smartagri.utils.ProgressOperator;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ReleaseBuyingPresenter extends ListPresenter<ReleaseBuyingContact.ReleaseBuyingView> implements ReleaseBuyingContact.Presenter<ReleaseBuyingContact.ReleaseBuyingView> {
    ServiceManager mServiceManager;

    @Inject
    public ReleaseBuyingPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void request(Activity activity, String type, String category, String title, String amount, String contacmobile, String contactor, String area, String content, String price, String acreage
            , String topcategory, String areaId, String mLatitude, String mLongitude) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("type", type)
                .addFormDataPart("category", category)
                .addFormDataPart("title", title)
                .addFormDataPart("amount", amount)
                .addFormDataPart("contacmobile", contacmobile)
                .addFormDataPart("contactor", contactor)
                .addFormDataPart("area", area)
                .addFormDataPart("content", content)
                .addFormDataPart("price", price)
                .addFormDataPart("acreage", acreage)
                .addFormDataPart("topcategory", topcategory)
                .addFormDataPart("mLatitude", mLatitude)
                .addFormDataPart("mLongitude", mLongitude)
                .addFormDataPart("area_id", areaId);
        mServiceManager.getMarketService()
                .addSupply(builder.build())
                .compose(Api.checkOn(mView))
                .lift(new ProgressOperator<>(activity, "正在提交..."))
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
                        mView.addBuy();
                    }
                });
    }

    @Override
    public void requestDetail(int id) {
        mServiceManager.getMarketService()
                .getSupplyDetail(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<SupplyDetail>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<SupplyDetail> supplyDetailResult) {
                        mView.getBuyDetail(supplyDetailResult.data);
                        if (TextUtils.isEmpty(supplyDetailResult.data.id)) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }

                    }
                });
    }

    @Override
    public void requestMenu(int id) {
        mServiceManager.getMarketService()
                .getMenu(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<SupplyMenu>> listResult) {
                        mView.getMenu(listResult.data);
                    }
                });
    }

    @Override
    public void requesEdit(Activity activity, int id, String type, String category, String title, String amount, String contacmobile, String contactor, String area, String content, String price, String acreage, String topcategory
            , String areaId, String mLatitude, String mLongitude) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("id", String.valueOf(id))
                .addFormDataPart("type", type)
                .addFormDataPart("category", category)
                .addFormDataPart("title", title)
                .addFormDataPart("amount", amount)
                .addFormDataPart("contacmobile", contacmobile)
                .addFormDataPart("contactor", contactor)
                .addFormDataPart("area", area)
                .addFormDataPart("content", content)
                .addFormDataPart("price", price)
                .addFormDataPart("acreage", acreage)
                .addFormDataPart("topcategory", topcategory)
                .addFormDataPart("mLatitude", mLatitude)
                .addFormDataPart("mLongitude", mLongitude)
                .addFormDataPart("area_id", areaId);
        mServiceManager.getMarketService()
                .editSupply(builder.build())
                .compose(Api.checkOn(mView))
                .lift(new ProgressOperator<>(activity, "正在提交..."))
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
                        mView.editBuy();
                    }
                });
    }


}
