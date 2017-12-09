package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/8/9.
 */

public interface MapFeedbackContract {
    interface MapFeedbackView extends BaseView{
        void success();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void addSuggest(String content,int type,int id,String phone);
    }
}
