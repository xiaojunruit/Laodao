package com.laoodao.smartagri.ui.user.contract;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.CerifyInfo;
import com.laoodao.smartagri.bean.CerifyMenu;

import java.io.File;
import java.util.List;


/**
 * Created by Administrator on 2017/8/14 0014.
 */

public interface ApplyCertificationContract {


    interface ApplyCertificationView extends ListBaseView {
        void menuSuccess(CerifyMenu menu);

        void infoSuccess(CerifyInfo info);

        void addSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestMenu();

        void requestInfo(String memberId);

        //认证农户
        void addCerify(String memberId, String cerifyType, String trueName, Activity activity, String[] cropsName, String[] cropsAcreage);

        //认证商家
        void addCerify(String memberId, String cerifyType, String trueName, Activity activity, File tradePic, String companyKindType);

        //认证专家
        void addCerify(String memberId, String cerifyType, String trueName, Activity activity, String specialFieldType, String goodatCrops, File idenCardPic);
    }
}