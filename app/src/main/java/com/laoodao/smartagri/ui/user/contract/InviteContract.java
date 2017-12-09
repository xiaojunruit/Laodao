package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.InviteInfo;

/**
 * Created by Long-PC on 2017/4/13.
 * 我的邀请Contact
 */

public interface InviteContract {
    interface InviteView extends BaseView{
        void showInviteInfo(InviteInfo inviteInfo);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getInviteInfo();
        void shareBack(String tag,int id);
    }
}
