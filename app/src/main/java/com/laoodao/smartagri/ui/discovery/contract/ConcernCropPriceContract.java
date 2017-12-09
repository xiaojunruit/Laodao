package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.PriceWonder;

import java.util.List;


/**
 * Created by Administrator on 2017/7/31 0031.
 */

public interface ConcernCropPriceContract {


    interface ConcernCropPriceView extends ListBaseView {
        void SuccessList(List<PriceWonder> priceWonders);

        void successAdd();

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList();

        void requestAdd(int id);

    }
}