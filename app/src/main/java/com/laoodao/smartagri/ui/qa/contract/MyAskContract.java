package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Ask;

import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 */

public interface MyAskContract {
    interface MyQAView extends ListBaseView {
        void showMyAskList(List<Ask> asks, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getMyAskList(int page);
    }
}
