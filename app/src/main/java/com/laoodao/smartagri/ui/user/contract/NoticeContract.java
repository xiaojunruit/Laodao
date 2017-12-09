package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.Notice;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public interface NoticeContract {
    interface NoticeView extends ListBaseView {
        void noticeSuucess(List<Notice> notices, boolean isRefresh);
        void isNewMessage(NewMessage message);
        void readMsgSucess(String num);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestNotice(int page, int type);
        void requestMessage();
        void readMsg(int id);
    }
}
