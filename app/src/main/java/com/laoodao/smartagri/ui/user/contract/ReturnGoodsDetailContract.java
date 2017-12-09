package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.ReturnGoodsDetail;

/**
 * Created by WORK on 2017/7/6.
 */

public interface ReturnGoodsDetailContract {
    interface ReturnGoodsDetailView extends ListBaseView{
        void setReturnGoodsDetail(ReturnGoodsDetail returnGoodsDetail);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestReturnGoodsDetail(int id);
    }
}
