package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/15.
 * 问答Contact
 */

public interface QAContract {
    interface QAView extends BaseView{
    }
    interface Presenter<T> extends BasePresenter<T>{
    }
}
