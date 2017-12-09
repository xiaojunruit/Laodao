package com.laoodao.smartagri.ui.user.presenter;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.google.gson.reflect.TypeToken;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.Suggestion;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.user.contract.IdeaFeedbackContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.ProgressOperator;
import com.laoodao.smartagri.utils.RxUtils;
import com.laoodao.smartagri.utils.UiUtils;

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
 * Created by WORK on 2017/4/13.
 */
public class IdeaFeedbackPresenter extends RxPresenter<IdeaFeedbackContract.IdeaFeedbackView> implements IdeaFeedbackContract.Presenter<IdeaFeedbackContract.IdeaFeedbackView> {
    ServiceManager mServiceManager;

    @Inject
    public IdeaFeedbackPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void getSuggestionList() {
        Observable<Result<List<Suggestion>>> fromNetWork = mServiceManager.getUserService().suggestionList().compose(RxUtils.rxCacheListHelper(Constant.IDEA_FEEDBACK));
        Observable<Result<List<Suggestion>>> observable = RxUtils.rxCreateDiskObservable(Constant.IDEA_FEEDBACK, new TypeToken<Result<List<Suggestion>>>() {
        }.getType());
        Observable.concat(observable, fromNetWork)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<Suggestion>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Suggestion>> listResult) {
                        mView.showSuggestionList(listResult.data);
                    }
                });

//        mServiceManager.getUserService()
//                .suggestionList()
//                .compose(Api.checkOn(mView))
//                .subscribe(new Subscriber<Result<List<Suggestion>>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Result<List<Suggestion>> result) {
//                        mView.showSuggestionList(result.data);
//                    }
//                });

    }

    @Override
    public void commitSuggestion(Activity activity, int type, String suggestion, String phone, List<LocalMedia> selectedImageList) {
        List<File> fileList = new ArrayList<>();
        fileList.clear();
        for (LocalMedia media : selectedImageList) {
            String path = media.getPath();
            fileList.add(new File(path));
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("type", String.valueOf(type))
                .addFormDataPart("suggestion", suggestion)
                .addFormDataPart("mobile", phone);

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
                        builder.addFormDataPart("pic[]", file.getPath(), body);
                    }
                    mServiceManager.getUserService()
                            .addSuggest(builder.build())
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
                                    mView.addSuggestionSuccess();
                                }
                            });
                });

    }
}
