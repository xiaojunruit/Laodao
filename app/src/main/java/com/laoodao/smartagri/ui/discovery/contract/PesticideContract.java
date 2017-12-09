package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Pesticide;
import com.laoodao.smartagri.bean.TruthQuery;

import java.util.List;


/**
 * Created by Administrator on 2017/5/16.
 */

public interface PesticideContract {


    interface PesticideView extends ListBaseView {
        void queryPesticide(List<TruthQuery> data, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestPesticide(int page, String number, String activePrinciple, String dose, String manufacturer);

    }
}