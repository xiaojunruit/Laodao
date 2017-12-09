package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.MechanicalType;

import java.util.List;

/**
 * Created by WORK on 2017/5/18.
 */

public interface SelectNewsMenuContract {
    interface SelectNewsMenuView extends BaseView {
        void newsMenuSuccess(List<MechanicalType> mechanicalTypeList);
        void newsMenuIdsSuucess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestNewsMenu();
        void requestNewsMenuIds(String ids);
    }
}
