package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;

/**
 * Created by WORK on 2017/7/5.
 */

public interface ReturnGoodsContract {
    interface ReturnGoodsView extends ListBaseView{

    }
    interface Presenter<T> extends BasePresenter<T>{
        void returnGoodsList(int page);
    }
}
