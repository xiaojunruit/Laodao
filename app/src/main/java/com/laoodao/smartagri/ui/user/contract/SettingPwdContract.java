package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/4/21.
 */

public interface SettingPwdContract {
    interface SettingPwdView extends BaseView {
        void settingSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void findPassword(String token, String pwd, String rePwd);
    }
}
