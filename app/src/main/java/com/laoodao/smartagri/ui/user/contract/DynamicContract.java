package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Notice;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public interface DynamicContract {
    interface DynamicView extends ListBaseView {
        void DynamicSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDynamic(int page, int type);
    }
}
