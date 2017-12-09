package com.laoodao.smartagri.ui.user.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.ListPresenter;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.ui.user.contract.AskCollectionContract;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class AskCollectionPresenter extends ListPresenter<AskCollectionContract.AskCollectionView> implements AskCollectionContract.Presenter<AskCollectionContract.AskCollectionView> {


    ServiceManager mServiceManager;

    @Inject
    public AskCollectionPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestList(int memberId, int page) {
        mServiceManager.getUserService()
                .askWonderList(memberId, page)
                .compose(transform())
                .subscribe(this::onPageLoaded);
    }

    @Override
    public void follow(int id, int position) {
        mServiceManager.getQAService()
                .addWonder(id)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> reuslt) {
                        mView.followSuccess(position, reuslt.data);
                    }
                });
    }

    @Override
    public void unfollow(int questionId, int position) {
        mServiceManager.getQAService()
                .delWonder(questionId)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Map<String, String>> reuslt) {
                        mView.unFollowSuccess(position, reuslt.data);
                    }
                });
    }
}