package com.laoodao.smartagri.ui.home.presenter;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.ui.home.contract.HomeContract;
import com.laoodao.smartagri.ui.home.contract.MainContract;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/11.
 */

public class MainPresenter extends RxPresenter<MainContract.MainView> implements MainContract
        .Presenter<MainContract.MainView> {

    ServiceManager mServiceManager;

    @Inject
    public MainPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requsetMessage() {
        mServiceManager.getUserService()
                .isNewMessage()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<NewMessage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<NewMessage> newMessageResult) {
                        mView.showMessage(newMessageResult.data);
                    }
                });
    }

    @Override
    public void requestInitMenu() {
        mServiceManager.getHomeService()
                .getInitMenu()
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<Map<String, String>> mapResult) {
                        if (Global.getInstance().isLoggedIn()) {
                            SharedPreferencesUtil.getInstance().putString(Constant.HUAN_XIN_PWD, mapResult.data.get("emcode"));
                            mView.loginHuanXin();
                        }
                    }
                });
    }

    @Override
    public void share(String tag, int id) {
        mServiceManager.getHomeService()
                .shareBack(tag,id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result<String> response) {

                    }
                });
    }


}
