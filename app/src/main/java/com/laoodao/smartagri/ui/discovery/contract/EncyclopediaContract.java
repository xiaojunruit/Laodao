package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.bean.CateList;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/20.
 */

public interface EncyclopediaContract {


    interface EncyclopediaView extends ListBaseView {
        void showCateList(CateList cropListBean);

        void showMenu(BaikeMenu menus);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestCateList();

        void requestShowMenu(int type);
    }
}