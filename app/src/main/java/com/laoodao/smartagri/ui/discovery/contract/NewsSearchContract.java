package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.News;

import java.util.List;

/**
 * Created by WORK on 2017/5/20.
 */

public interface NewsSearchContract {
    interface NewsSearchView extends ListBaseView{
//        void newsSearchSuccess(List<News> newsList,boolean isRefresh);
        void newsSearchSuccess();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestNewsSearch(int gcId,int page,String kw);
    }
}
