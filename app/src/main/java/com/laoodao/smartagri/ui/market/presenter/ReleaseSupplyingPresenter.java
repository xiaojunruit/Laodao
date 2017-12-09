package com.laoodao.smartagri.ui.market.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.market.contact.ReleaseSupplyingContact;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.ProgressOperator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ReleaseSupplyingPresenter extends ListPresenter<ReleaseSupplyingContact.ReleaseSupplyingView> implements ReleaseSupplyingContact.Presenter<ReleaseSupplyingContact.ReleaseSupplyingView> {
    ServiceManager mServiceManager;

    @Inject
    public ReleaseSupplyingPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void request(Activity activity, int type, String category, String title, String price, String amount, String contactor, String contacmobile, String area, String content, List<LocalMedia> conver, String acreage
            , String topcategory, String mLatitude, String mLongitude, String areaId) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("type", String.valueOf(type))
                .addFormDataPart("category", category)
                .addFormDataPart("title", title)
                .addFormDataPart("price", price)
                .addFormDataPart("amount", amount)
                .addFormDataPart("contactor", contactor)
                .addFormDataPart("contacmobile", contacmobile)
                .addFormDataPart("area", area)
                .addFormDataPart("content", content)
                .addFormDataPart("acreage", acreage)
                .addFormDataPart("mLatitude", mLatitude)
                .addFormDataPart("mLongitude", mLongitude)
                .addFormDataPart("topcategory", topcategory)
                .addFormDataPart("area_id", areaId);
        List<File> fileList = new ArrayList<>();
        for (LocalMedia image : conver) {
            fileList.add(new File(image.getPath()));
        }

        Observable.from(fileList)
                .flatMap(localMedia ->
                        Luban.get(LDApplication.getInstance()).load(localMedia).putGear(Luban.THIRD_GEAR).asObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Observable.empty())
                .toList()
                .lift(new ProgressOperator<>(activity, "正在提交..."))
                .subscribe(files -> {
                    for (File file : files) {
                        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
                        builder.addFormDataPart("conver[]", file.getPath(), body);
                    }

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
                                    LogUtil.e("onError->" + e.getMessage());
                                }

                                @Override
                                public void onNext(Response response) {
                                    mView.addSupply();
                                }
                            });
                });
    }

    @Override
    public void requestType(int id) {
        mServiceManager.getMarketService()
                .getMenu(id)
                .compose(transform())
                .subscribe(new Subscriber<Result<List<SupplyMenu>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<List<SupplyMenu>> listResult) {
                        mView.typeSuccess(listResult.data);
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
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<SupplyDetail> supplyDetailResult) {
                        mView.getDetail(supplyDetailResult.data);
                        if (TextUtils.isEmpty(supplyDetailResult.data.id)) {
                            mView.onEmpty();
                        } else {
                            mView.onContent();
                        }

                    }
                });
    }

    @Override
    public void requesEdit(Activity activity, int id, int type, String category, String title, String price, String amount, String contactor, String contacmobile, String area, String content, List<LocalMedia> conver, String acreage,
                           String topcategory, String mLatitude, String mLongitude, String areaId) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("id", String.valueOf(id))
                .addFormDataPart("type", String.valueOf(type))
                .addFormDataPart("category", category)
                .addFormDataPart("title", title)
                .addFormDataPart("price", price)
                .addFormDataPart("amount", amount)
                .addFormDataPart("contactor", contactor)
                .addFormDataPart("contacmobile", contacmobile)
                .addFormDataPart("area", area)
                .addFormDataPart("content", content)
                .addFormDataPart("acreage", acreage)
                .addFormDataPart("topcategory", topcategory)
                .addFormDataPart("area_id", areaId);
        List<File> fileList = new ArrayList<>();
        for (LocalMedia image : conver) {
            fileList.add(new File(image.getPath()));
        }

        Observable.from(fileList)
                .flatMap(localMedia ->
                        Luban.get(LDApplication.getInstance()).load(localMedia).putGear(Luban.THIRD_GEAR).asObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Observable.empty())
                .toList()
                .lift(new ProgressOperator<>(activity, "正在提交..."))
                .subscribe(files -> {
                    for (File file : files) {
                        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
                        builder.addFormDataPart("conver[]", file.getPath(), body);
                    }

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
                                    LogUtil.e("onError->" + e.getMessage());
                                }

                                @Override
                                public void onNext(Response response) {
                                    mView.editSupply();
                                }
                            });
                });
    }
}
