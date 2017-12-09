package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.ChatAvatar;

import java.util.List;

/**
 * Created by WORK on 2017/8/16.
 */

public interface ContactsListContract {
    interface ChatListView extends BaseView{
        void setAvatar(List<ChatAvatar> list);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getAvatar(String uids);
    }
}
