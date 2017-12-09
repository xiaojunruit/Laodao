package com.laoodao.smartagri.ui.qa.presenter;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.AskSuccess;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.qa.contract.AskContract;
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
 * Created by WORK on 2017/4/18.
 */

public class AskPresenter extends RxPresenter<AskContract.AskView> implements AskContract.Presenter<AskContract.AskView> {
    ServiceManager mServiceManager;

    @Inject
    public AskPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void releaseQuetion(Activity activity, String title, String content, String longitude, String latitude, String city, List<Plant> plantList, List<LocalMedia> selectedImageList) {
        List<File> fileList = new ArrayList<>();
        fileList.clear();
        for (LocalMedia media : selectedImageList) {
            String path = media.getPath();
            fileList.add(new File(path));
        }
        String categoryIds = "";
        for (Plant plant : plantList) {
            categoryIds += plant.id + ",";
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("longitude", longitude)
                .addFormDataPart("latitude", latitude)
                .addFormDataPart("category_ids", categoryIds)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("area_name", city);
        Observable.from(fileList)
                .flatMap(localMedia -> Luban.get(activity).load(localMedia).putGear(Luban.THIRD_GEAR).asObservable())
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
                        builder.addFormDataPart("conver[]", file.getPath(), body);
                    }
                    mServiceManager.getQAService()
                            .addQA(builder.build())
                            .compose(Api.checkOn(mView))
                            .lift(new ProgressOperator<>(activity, "正在提交..."))
                            .subscribe(new Subscriber<Result<AskSuccess>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(Result<AskSuccess> askSuccessResult) {
                                    mView.releaseSuccess(askSuccessResult.data);
                                }
                            });
                });
    }
}
