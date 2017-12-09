package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Baike;

import java.util.List;


/**
 * Created by Administrator on 2017/5/20.
 */

public interface BaikeSearchContract {


    interface BaikeSearchView extends ListBaseView {
        void showList();

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(int page, String kw);

    }
}