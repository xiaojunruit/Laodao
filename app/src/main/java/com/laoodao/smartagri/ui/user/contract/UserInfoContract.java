package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import okhttp3.RequestBody;

/**
 * Created by Long-PC on 2017/4/13.
 */

public interface UserInfoContract {
    interface UserInfoView extends BaseView {
        void avatarSuccess(String avatar);

        void updateSexSuccess(int sex);

        void updateAreaSuccess(String area);

        void requestPermissionSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void updateAvatar(File file);

        void updateSex(int sex);

        void updateArea(String area);

        void requestCarmeraPermission(RxPermissions permissions);
    }
}
