package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MechanicalType;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/21.
 */

public interface NewsContract {


    interface NewsView extends ListBaseView {
        void newMenuSuccess(List<MechanicalType> mechanicalTypeList);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestNewMenu();
    }
}