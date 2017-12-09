package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.ReplySuccess;

import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 2017/4/17.
 */

public interface QuestionDetailContract {
    interface QuestionDetailView extends ListBaseView {
        void showQuestionDetail(Question qa);

        void showAnswerList(List<Answer> answers, boolean isRefresh);

        void followChange(Map<String, String> data);

        void replySuccess(ReplySuccess data, int position);

        void praiseSuccess(int position, Map<String, String> data);

        void answerSuccess(Answer data);

        void collectSuccess();

        void shareSuccess();

        void UserFollowSuccess(int position);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getQuestionDetail(int id);

        void getAnswerList(int askId, int page);

        void unfollow(int questionId);

        void follow(int questionId);

        void reply(int id, String reply, int position);

        void praise(int i, int position);

        void answer(int type_answer, int mQuestionId, String reply);

        void collect(int mQuestionId);

        void share(String tag, int id);

        void addUserFollow(int id, int position);

    }
}
