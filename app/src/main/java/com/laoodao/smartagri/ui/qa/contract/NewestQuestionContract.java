package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Question;

import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 2017/4/15.
 */

public interface NewestQuestionContract {
    interface NewestQuestionView extends ListBaseView {
        void showNewsQuestionList(List<Question> questionList, boolean isRefresh);

        void followSuccess(int dataPosition, Map<String, String> data);
        void shareSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getNewsQuestionList(int page);

        void follow(int questionId, int position);

        void unfollow(int questionId, int position);

        void share(String tag,int id);
    }
}
