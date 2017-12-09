package com.laoodao.smartagri.ui.farmland.presenter;

import android.app.Activity;
import android.widget.TextView;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.City;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.OperationDetail;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.farmland.contract.AddOperationContract;
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
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by WORK on 2017/4/25.
 */

public class AddOperationPresenter extends RxPresenter<AddOperationContract.AddOperationView> implements AddOperationContract.Presenter<AddOperationContract.AddOperationView> {
    ServiceManager mServiceManager;

    @Inject
    public AddOperationPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestAddOperation(Activity activity, int id, int farmId, int type, String typeName, String remark, String operateTime, String artificial, String[] cropsName, String[] cropsMoney, String[] machineName, String[] machineMoney, List<LocalMedia> images) {


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("id", String.valueOf(id))
                .addFormDataPart("farm_id", String.valueOf(farmId))
                .addFormDataPart("type", String.valueOf(type))
                .addFormDataPart("type_name", typeName)
                .addFormDataPart("remark", remark)
                .addFormDataPart("operate_time", operateTime)
                .addFormDataPart("artificial", artificial);

        Observable.from(cropsName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1 -> {
                    builder.addFormDataPart("crops_name[]", action1);
                });
        Observable.from(cropsMoney)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1 -> {

                    builder.addFormDataPart("crops_money[]", action1);
                });
        Observable.from(machineName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1 -> {
                    builder.addFormDataPart("machine_name[]", action1);
                });
        Observable.from(machineMoney)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1 -> {
                    builder.addFormDataPart("machine_money[]", action1);
                });
        if (images != null) {
            List<File> fileList = new ArrayList<>();
            fileList.clear();
            for (LocalMedia image : images) {
                String path = image.getPath();
                fileList.add(new File(path));
            }
            Observable.from(fileList)
                    .flatMap(localMedia ->
                            Luban.get(LDApplication.getInstance()).load(localMedia).putGear(Luban.THIRD_GEAR).asObservable())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(throwable -> {

                    })
                    .doOnError(throwable -> Observable.empty())
                    .toList()
                    .lift(new ProgressOperator<>(activity, "正在提交..."))
                    .subscribe(files -> {
                        for (File file : files) {
                            RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
                            builder.addFormDataPart("images[]", file.getPath(), body);
                        }
                        farmlandService(activity, builder);


                    });
        } else {
            farmlandService(activity, builder);
        }
    }


    private void farmlandService(Activity activity, MultipartBody.Builder builder) {
        mServiceManager.getFarmlandService()
                .addOperation(builder.build())
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
                        mView.addOperationSuccess();
                    }
                });
    }

    @Override
    public void requestMechanicalType(TextView view) {
        mServiceManager.getFarmlandService()
                .mechanicalType()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<MechanicalType>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<List<MechanicalType>> mechanicalTypeResult) {
                        mView.mechanicalTypeSuccess(mechanicalTypeResult.data, view);
                    }
                });
    }

    @Override
    public void requestOperationDetail(int id) {
        mServiceManager.getFarmlandService()
                .operationDetail(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<OperationDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<OperationDetail> result) {
                        mView.operationDetial(result.data);
                    }
                });
    }


}
