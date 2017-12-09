package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Question;

import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 2017/4/17.
 * 关注内容fragment
 */

public interface FollowContentContract {
    interface FollowContentView extends ListBaseView {
        void initData(List<Question> questionList, boolean isRefresh);

        void followSuccess(int dataPosition, Map<String, String> data);

        void followPlantSuccess();

        void shareSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getFollowContent(String type, int page);

        void follow(int questionId, int position);

        void unfollow(int questionId, int position);

        void followPlant(String ids);

        void share(String tag,int id);
    }
}
