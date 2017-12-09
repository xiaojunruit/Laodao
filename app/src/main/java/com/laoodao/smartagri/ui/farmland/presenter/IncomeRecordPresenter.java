package com.laoodao.smartagri.ui.farmland.presenter;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.farmland.contract.IncomeRecordContract;
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
 * Created by Administrator on 2017/4/23.
 */

public class IncomeRecordPresenter extends RxPresenter<IncomeRecordContract.FarmIncomeView> implements IncomeRecordContract.Presenter<IncomeRecordContract.FarmIncomeView> {
    ServiceManager mServiceManager;

    @Inject
    public IncomeRecordPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestDate(int type) {
        mServiceManager
                .getFarmlandService()
                .getClassification(type)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<RecordClassification>>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<RecordClassification>> listResult) {
                        mView.initData(listResult.data);
                    }
                });
    }

    @Override
    public void requestAccounting(Activity activity, int id, int typeId, int type, String money, String remark, String time, List<LocalMedia> images) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("id", String.valueOf(id))
                .addFormDataPart("operate_type", String.valueOf(typeId))
                .addFormDataPart("account_type", String.valueOf(type))
                .addFormDataPart("account", money)
                .addFormDataPart("remark", remark)
                .addFormDataPart("date", time);
        List<File> fileList = new ArrayList<File>();
        fileList.clear();
        for (LocalMedia image : images) {
            fileList.add(new File(image.getPath()));
        }

        Observable.from(fileList)
                .flatMap(localMedia ->
                        Luban.get(LDApplication.getInstance()).load(localMedia).putGear(Luban.THIRD_GEAR).asObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Observable.empty())
                .toList()
                .lift(new ProgressOperator<List<File>>(activity,"正在提交..."))
                .subscribe(files -> {
                    for (File file : files) {
                        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
                        builder.addFormDataPart("images[]", file.getPath(), body);
                    }

                    mServiceManager.getFarmlandService()
                            .accounting(builder.build())
                            .compose(Api.checkOn(mView))
                            .lift(new ProgressOperator<>(activity,"正在提交..."))
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
                                    mView.accountingSuccess();
                                }
                            });

                });
    }
}
