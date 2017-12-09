package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Discovercat;

import java.util.List;


/**
 * Created by Administrator on 2017/6/21.
 */

public interface MenuMoreContract {


    interface MenuMoreView extends ListBaseView {
        void allMenu(List<Discovercat> data);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void allMenuRequest(int type);
    }
}