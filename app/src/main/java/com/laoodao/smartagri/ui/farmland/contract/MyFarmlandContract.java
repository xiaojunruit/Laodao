package com.laoodao.smartagri.ui.farmland.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Farmland;
import com.laoodao.smartagri.bean.base.Pagination;

/**
 * Created by WORK on 2017/4/23.
 */

public interface MyFarmlandContract {
    interface MyFarmlandView extends ListBaseView {

        void showFarmlandList(Pagination<Farmland> data,boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getFarmlandList(int page);
    }
}
