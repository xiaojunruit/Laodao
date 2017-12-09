package com.laoodao.smartagri.ui.market.contact;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.SupplyMy;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface ReleaseBuyContact {
    interface ReleaseBuyView extends ListBaseView {
        void initData(List<SupplyMy> data, boolean isRefresh);

        void deleteData();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDate(int page, int type);

        void requestDel(int id);
    }
}
