package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.Question;

import java.util.List;

/**
 * Created by WORK on 2017/8/1.
 */

public interface PriceQuotationSearchContract {
    interface PriceQuotationSearchView extends ListBaseView {
        void showHotKeyword(List<Keyword> data);

        void sucess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getHotKeyword();

        void requestData( int cateId, int page, int timeType, String keyword, String pos, int areaId);
    }
}
