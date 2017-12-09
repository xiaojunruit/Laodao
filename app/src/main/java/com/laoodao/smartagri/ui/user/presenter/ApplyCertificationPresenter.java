package com.laoodao.smartagri.ui.user.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.CerifyInfo;
import com.laoodao.smartagri.bean.CerifyMenu;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.ApplyCertificationContract;
import com.laoodao.smartagri.utils.ProgressOperator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;


/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class ApplyCertificationPresenter extends ListPresenter<ApplyCertificationContract.ApplyCertificationView> implements ApplyCertificationContract.Presenter<ApplyCertificationContract.ApplyCertificationView> {


    ServiceManager mServiceManager;

    @Inject
    public ApplyCertificationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestMenu() {
        mServiceManager.getUserService()
                .getCerifyMenu()
                .compose(transform())
                .subscribe(new Subscriber<Result<CerifyMenu>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<CerifyMenu> cerifyMenuResult) {
                        mView.menuSuccess(cerifyMenuResult.data);
                    }
                });
    }

    @Override
    public void requestInfo(String memberId) {
        mServiceManager.getUserService()
                .getCerifyInfo(memberId)
                .compose(transform())
                .subscribe(new Subscriber<Result<CerifyInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.onError();
                    }

                    @Override
                    public void onNext(Result<CerifyInfo> cerifyInfoResult) {
                        mView.infoSuccess(cerifyInfoResult.data);
                        if (cerifyInfoResult.data == null) {
                            mView.onError();
                        } else {
                            mView.onContent();
                        }
                    }
                });

    }

    @Override
    public void addCerify(String memberId, String cerifyType, String trueName, Activity activity, String[] cropsName, String[] cropsAcreage) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("member_id", memberId)
                .addFormDataPart("cerify_type", cerifyType)
                .addFormDataPart("true_name", trueName);

        for (String s : cropsName) {
            builder.addFormDataPart("crops_name[]", s);
        }
        for (String s : cropsAcreage) {
            builder.addFormDataPart("crops_acreage[]", s);
        }
        addServer(activity, builder);
    }

    @Override
    public void addCerify(String memberId, String cerifyType, String trueName, Activity activity, File tradePic, String companyKindType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("member_id", memberId)
                .addFormDataPart("cerify_type", cerifyType)
                .addFormDataPart("true_name", trueName)
                .addFormDataPart("company_kind_type", companyKindType);

        if (!TextUtils.isEmpty(tradePic.getParent())) {
            Observable.just(tradePic)
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
                            builder.addFormDataPart("trade_pic[]", file.getPath(), body);
                        }
                        addServer(activity, builder);
                    });
        } else {
            addServer(activity, builder);
        }
//        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), tradePic);
//        if (!TextUtils.isEmpty(tradePic.getParent())) {
//            builder.addFormDataPart("trade_pic[]", tradePic.getPath(), body);
//        }

    }

    @Override
    public void addCerify(String memberId, String cerifyType, String trueName, Activity activity, String specialFieldType, String goodatCrops, File idenCardPic) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("member_id", memberId)
                .addFormDataPart("cerify_type", cerifyType)
                .addFormDataPart("true_name", trueName)
                .addFormDataPart("special_field_type", specialFieldType)
                .addFormDataPart("goodat_crops", goodatCrops);

        if (!TextUtils.isEmpty(idenCardPic.getPath())) {
            Observable.just(idenCardPic)
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
                            builder.addFormDataPart("iden_card_pic[]", file.getPath(), body);
                        }
                        addServer(activity, builder);
                    });
        } else {
            addServer(activity, builder);
        }

//        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), idenCardPic);
//        if (!TextUtils.isEmpty(idenCardPic.getPath())) {
//            builder.addFormDataPart("iden_card_pic[]", idenCardPic.getPath(), body);
//        }
//        addServer(activity, builder);
    }

    private void addServer(Activity activity, MultipartBody.Builder builder) {
        mServiceManager.getUserService()
                .addCerify(builder.build())
                .compose(Api.checkOn(mView))
                .lift(new ProgressOperator<>(activity, "正在提交"))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        mView.addSuccess();
                    }
                });

    }
}