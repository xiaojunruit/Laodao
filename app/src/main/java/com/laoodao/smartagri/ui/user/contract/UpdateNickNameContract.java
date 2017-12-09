package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/15.
 * 修改昵称contact
 */

public interface UpdateNickNameContract {
    interface BasicInfoView extends BaseView {
        void updateSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void updateNickName(String nickName);
    }
}
