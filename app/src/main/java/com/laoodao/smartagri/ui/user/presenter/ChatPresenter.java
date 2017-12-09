package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.ChatContract;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by WORK on 2017/8/16.
 */

public class ChatPresenter extends RxPresenter<ChatContract.ChatView> implements ChatContract.Presenter<ChatContract.ChatView> {
    ServiceManager mServiceManager;

    @Inject
    public ChatPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }


    @Override
    public void setSendContent(String tag, int type, String toName, String content, File image, File voice) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("tag", tag)
                .addFormDataPart("type", String.valueOf(type))
                .addFormDataPart("to_member_name", toName)
                .addFormDataPart("content", content);
        if (image.length() > 1) {
            RequestBody content1 = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), image);
            builder.addFormDataPart("image", image.getPath(), content1);
            builder.addFormDataPart("voice", "");
        } else if (voice.length() > 1) {
            RequestBody content1 = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), voice);
            builder.addFormDataPart("voice", voice.getPath(), content1);
            builder.addFormDataPart("image", "");
        }

        mServiceManager.getUserService()
                .setSendContent(builder.build())
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    @Override
    public void getAvatar(String uids) {
        mServiceManager.getUserService()
                .getAvatar(uids)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<List<ChatAvatar>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<ChatAvatar>> listResult) {
                        mView.setAvatar(listResult.data);
                    }
                });
    }
}
