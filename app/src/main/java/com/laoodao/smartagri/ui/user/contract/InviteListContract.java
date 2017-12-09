package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.Invite;
import com.laoodao.smartagri.bean.InviteList;

import java.util.List;

/**
 * Created by Long-PC on 2017/4/13.
 */

public interface InviteListContract {
    interface IniteListView extends ListBaseView{
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestInviteListData(int page);
    }
}
