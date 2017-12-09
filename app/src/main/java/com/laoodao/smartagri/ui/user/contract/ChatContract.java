package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.ChatAvatar;

import java.io.File;
import java.util.List;

/**
 * Created by WORK on 2017/8/16.
 */

public interface ChatContract {
    interface ChatView extends BaseView{
        void setAvatar(List<ChatAvatar> list);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void setSendContent(String tag, int type, String toName, String content, File image, File voice);
        void getAvatar(String uids);
    }
}
