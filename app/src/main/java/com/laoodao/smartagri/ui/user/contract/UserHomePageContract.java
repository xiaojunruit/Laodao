package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.UserHomePage;
import com.laoodao.smartagri.bean.UserHomePageHead;

import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 2017/8/14.
 */

public interface UserHomePageContract {

    interface UserHomePageView extends ListBaseView {


        void setUserHomePageHeader(UserHomePageHead data);

        void addWonderSuccess();

        void setListSize(int size);

        void followSuccess(int dataPosition, Map<String, String> data);

        void unFollowSuccess(int dataPosition, Map<String, String> data);
    }


    interface Presenter<T> extends BasePresenter<T> {


        void rquestHeaderData(int id);

        void addWonder(int id);

        void userHomePageData(int page, int id, int type);

        void follow(int id, int position);

        void unfollow(int questionId, int position);

        void share(String tag,int id);
    }
}
