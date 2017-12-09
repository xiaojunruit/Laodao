package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.TruthQuery;

import java.util.List;

/**
 * Created by WORK on 2017/5/3.
 */

public interface TruthQuerySearchContract {
    interface TruthQuerySearchView extends ListBaseView{
        void ShowSearch(List<TruthQuery> truthQuery, boolean isRefresh);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestSearch(int page, String tag);
    }
}
