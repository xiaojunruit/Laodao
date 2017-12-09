package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.UserInfoContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by Long-PC on 2017/4/13.
 */

public class UserInfoPresenter extends RxPresenter<UserInfoContract.UserInfoView> implements UserInfoContract.Presenter<UserInfoContract.UserInfoView> {
    ServiceManager mServiceManager;

    @Inject
    public UserInfoPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void updateAvatar(File file) {
        Luban.get(LDApplication.getInstance())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file1 -> {
                    RequestBody content = RequestBody.create(MediaType.parse("image/jpeg"), file1);
                    mServiceManager.getUserService()
                            .updateAvatar(content)
                            .compose(Api.checkOn(mView))
                            .subscribe(new Subscriber<Result<Map<String, String>>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtil.e("onError->" + e.getMessage());
                                }

                                @Override
                                public void onNext(Result<Map<String, String>> mapResult) {
                                    mView.avatarSuccess(mapResult.data.get("avatar"));
                                }
                            });
                });
    }

    @Override
    public void updateSex(int sex) {
        mServiceManager.getUserService()
                .updateSex(sex)
                .compose(Api.checkOn(mView))
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
                        mView.updateSexSuccess(sex);
                    }
                });
    }

    @Override
    public void updateArea(String area) {
        mServiceManager.getUserService()
                .updateArea(area)
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
                        mView.updateAreaSuccess(area);
                    }
                });
    }

    @Override
    public void requestCarmeraPermission(RxPermissions permissions) {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {
                mView.requestPermissionSuccess();
            }

            @Override
            public void failure() {
                UiUtils.makeText("请求权限失败,请前往设置开启权限");
            }
        }, permissions);
    }
}
