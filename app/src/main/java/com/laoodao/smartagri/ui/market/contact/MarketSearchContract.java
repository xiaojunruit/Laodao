package com.laoodao.smartagri.ui.market.contact;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.Supplylists;

import java.util.List;


/**
 * Created by Administrator on 2017/5/3.
 */

public interface MarketSearchContract {


    interface MarketSearchView extends ListBaseView {
        void showSupplyList(List<Supplylists> items, boolean isRefresh);

        void Search();

        void showHostWords(List<Keyword> data);

        void mySearch(List<Keyword> data);

        void deleteHistory();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void searchSupply(int page, String keyword);

        void requestAddSearch(String keywords);

        void getHostWords();

        void getMySearch();

        void requestDelHistory(int id);
    }
}