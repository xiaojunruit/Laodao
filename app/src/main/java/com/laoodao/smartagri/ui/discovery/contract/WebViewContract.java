package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/7/23.
 */

public interface WebViewContract {
    interface WebView extends BaseView {
        void shareBackSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void shareBack(String tag,int id);
    }
}
