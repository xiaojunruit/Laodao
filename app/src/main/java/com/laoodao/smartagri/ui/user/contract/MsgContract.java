package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MyMessage;
import com.laoodao.smartagri.bean.NewMessage;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public interface MsgContract {
    interface MsgView extends BaseView {
        void isNewMessage(NewMessage message);
        void clearMsgSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestMessage();
        void clearMsg(int type);
    }
}
