package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MyAnswer;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public interface MyAnswerContract {
    interface MyAnswerQAView extends ListBaseView {
        void myAnswerSuccess(List<MyAnswer> myAnswers, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getMyAnswerList(int page);
    }
}
