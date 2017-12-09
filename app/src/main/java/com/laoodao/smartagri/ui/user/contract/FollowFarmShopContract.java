package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.WonderStore;

import java.util.List;


/**
 * Created by Administrator on 2017/8/14 0014.
 */

public interface FollowFarmShopContract {


    interface FollowFarmShopView extends ListBaseView {
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(String memberId, int page);

        void AddOrDelFollow(String id);
    }
}