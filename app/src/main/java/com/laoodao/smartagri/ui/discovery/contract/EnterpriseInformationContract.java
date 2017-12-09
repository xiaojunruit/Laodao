package com.laoodao.smartagri.ui.discovery.contract;

import android.app.Activity;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.Enterprise.Enterprise;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddress;

import java.util.List;


/**
 * Created by Administrator on 2017/6/28.
 */

public interface EnterpriseInformationContract {


    interface EnterpriseInformationView extends BaseView {
        void setEnterprise(Enterprise enterprise);
        void setEnterpriseAddress(List<EnterpriseAddress> address);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void enterpriseSelect(Activity activity,String url, String enterprisecode, String validateCode, String enterprisename, String ASCRIPTIONA, String ASCRIPTIONB, String searchYear);
        void getCottonArea();
    }
}