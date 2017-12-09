package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Notice;

import java.util.List;

/**
 * Created by WORK on 2017/5/4.
 */

public interface ReplyContract {
    interface ReplyView extends ListBaseView{
        void replySuucess();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestReply(int page,int type);
    }
}
