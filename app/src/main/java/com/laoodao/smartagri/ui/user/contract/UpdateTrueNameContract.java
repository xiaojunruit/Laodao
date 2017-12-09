package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;


/**
 * Created by Administrator on 2017/8/21 0021.
 */

public interface UpdateTrueNameContract {


    interface UpdateTrueNameView extends BaseView {
        void successUpdate(String name);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestUpdate(String name);
    }
}