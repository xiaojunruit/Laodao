package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.MsgDetail;

/**
 * Created by WORK on 2017/6/22.
 */

public interface MessageDetailContract {
    interface MessageDetailView extends BaseView {
        void setMsgDetail(MsgDetail msgDetail);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getMsgDetail(int id,String objType);
    }

}
