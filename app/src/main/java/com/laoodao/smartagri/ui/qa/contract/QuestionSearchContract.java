package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.Question;

import java.util.List;


/**
 * Created by 欧源 on 2017/5/2.
 */

public interface QuestionSearchContract {


    interface QuestionSearchView extends ListBaseView {

        void showHotKeyword(List<Keyword> data);

        void showHistory(List<Keyword> data);

        void showQuestionList(List<Question> items, boolean isRefresh);

        void delteSuccess();

        void addSearchSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {

        void getHotKeyword();

        void getHistorySearch();

        void searchQuestion(int page, String keyword);

        void deleteHistory(int id);

        void addSearch(String keyword);
    }
}