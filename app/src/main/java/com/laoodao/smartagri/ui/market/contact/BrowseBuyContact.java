package com.laoodao.smartagri.ui.market.contact;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Collection;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface BrowseBuyContact {
    interface BrowseBuyView extends ListBaseView {
        void initData(List<Collection> list, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestData(int page, int type);
    }
}
