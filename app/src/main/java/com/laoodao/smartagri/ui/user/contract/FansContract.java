package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.WonderUser;

import java.util.List;


/**
 * Created by Administrator on 2017/8/14 0014.
 */

public interface FansContract {


    interface FansView extends ListBaseView {
        // void SuccessList(List<WonderUser> wonderUsers);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(int memberId, int page, int type);

        void Follow(int id);
    }
}