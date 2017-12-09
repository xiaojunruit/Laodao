package com.laoodao.smartagri.ui.farmland.presenter;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.ui.farmland.contract.AddFarmlandContract;
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
 * Created by 欧源 on 2017/4/24.
 */

public class AddFarmlandPresenter extends RxPresenter<AddFarmlandContract.AddFarmlandView> implements AddFarmlandContract.Presenter<AddFarmlandContract.AddFarmlandView> {


    ServiceManager mServiceManager;

    @Inject
    public AddFarmlandPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void addFarmland(Activity activity, int id, String cropName, String farmlandDesc, String acreage,
                            String addressDesc, String provinceId, List<LocalMedia> selectedImageList) {

        List<File> fileList = new ArrayList<>();
        fileList.clear();
        for (LocalMedia media : selectedImageList) {
            String path = media.getPath();
            fileList.add(new File(path));
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("area_id", provinceId)
                .addFormDataPart("address", addressDesc)
                .addFormDataPart("acreage", acreage)
                .addFormDataPart("crops_name", cropName)
                .addFormDataPart("id", String.valueOf(id))
                .addFormDataPart("farmlandDesc", farmlandDesc);
        Observable.from(fileList)
                .flatMap(localMedia -> Luban.get(activity).load(localMedia).putGear(Luban.THIRD_GEAR).asObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                })
                .doOnError(throwable -> Observable.empty())
                .toList()
                .lift(new ProgressOperator<List<File>>(activity, "正在提交..."))
                .subscribe(files -> {
                    for (File file : files) {
                        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
                        builder.addFormDataPart("images[]", file.getPath(), body);
                    }
                    mServiceManager.getFarmlandService()
                            .addFarmland(builder.build())
                            .compose(Api.checkOn(mView))
                            .lift(new ProgressOperator<>(activity, "正在提交..."))
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
                });


    }
}
