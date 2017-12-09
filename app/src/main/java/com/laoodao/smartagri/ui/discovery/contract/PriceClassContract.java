package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;


/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface PriceClassContract {


    interface PriceClassView extends ListBaseView {

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestData(int cateId, int page, int timeType, String keyword, String pos,int areaId);
    }
}