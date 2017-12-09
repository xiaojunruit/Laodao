package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;


/**
 * Created by Administrator on 2017/8/15 0015.
 */

public interface IntroductionContract {


    interface IntroductionView extends BaseView {
        void updateSignatureSuccess(String signature);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void updateSignature(String signature);
    }
}