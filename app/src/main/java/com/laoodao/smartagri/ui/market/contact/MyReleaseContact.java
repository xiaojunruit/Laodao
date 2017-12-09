package com.laoodao.smartagri.ui.market.contact;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface MyReleaseContact {
    interface MyReleaseView extends BaseView {

    }

    interface Presenter<T> extends BasePresenter<T> {
        void share(String tag,int id);
    }
}
