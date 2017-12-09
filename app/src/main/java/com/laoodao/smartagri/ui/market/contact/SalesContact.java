package com.laoodao.smartagri.ui.market.contact;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyArea;
import com.laoodao.smartagri.bean.Supplylists;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */

public interface SalesContact {
    interface SalesView extends ListBaseView {

        void getDateMenuList(List<SupplyMenu> dateType);

        void getCategoryMenuList(List<SupplyMenu> menuType);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestSales(int page, int type, String keyword, String categoryId, String areaId, String timeId);

        void requestDateType();

        void requestMenuType();

    }
}
