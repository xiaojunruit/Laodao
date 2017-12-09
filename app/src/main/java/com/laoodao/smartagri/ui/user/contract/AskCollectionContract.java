package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;

import java.util.Map;


/**
 * Created by Administrator on 2017/8/19 0019.
 */

public interface AskCollectionContract {


    interface AskCollectionView extends ListBaseView {
        void followSuccess(int dataPosition, Map<String, String> data);

        void unFollowSuccess(int dataPosition, Map<String, String> data);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(int memberId, int page);
        void follow(int id, int position);

        void unfollow(int questionId, int position);
    }
}