package com.laoodao.smartagri.ui.farmland.contract;

import android.app.Activity;
import android.widget.TextView;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.OperationDetail;

import java.util.List;

/**
 * Created by WORK on 2017/4/25.
 */

public interface AddOperationContract{
    interface AddOperationView extends BaseView{
        void addOperationSuccess();
        void mechanicalTypeSuccess(List<MechanicalType> mechanicalTypeList, TextView view);
        void operationDetial(OperationDetail operationDetail);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestAddOperation(Activity activity,int id, int farmId, int type, String typeName, String remark, String operateTime
        , String artificial, String[] cropsName, String[] cropsMoney, String[] machineName, String[] machineMoney, List<LocalMedia> images);
        void requestMechanicalType(TextView view);
        void requestOperationDetail(int id);
    }
}
