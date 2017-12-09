package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.bean.Ask;
import com.laoodao.smartagri.bean.MyAnswer;
import com.laoodao.smartagri.bean.WonderAnswer;

import java.util.List;

/**
 * Created by WORK on 2017/4/24.
 */

public interface WonderAnswerContract {
    interface WonderAnswerView extends ListBaseView {
        void showWonderAnswerList(List<WonderAnswer> askList, boolean isRefresh);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getWonderAnswerList(int page);
    }
}
