package com.laoodao.smartagri.ui.farmland.contract;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.SupplyMarketing;
import com.laoodao.smartagri.bean.base.AccountDetail;
import com.laoodao.smartagri.bean.base.Result;

import java.util.List;

/**
 * Created by Administrator on 2017/4/23.
 */

public interface IncomeRecordContract {
    interface FarmIncomeView extends BaseView {
        void initData(List<RecordClassification> recordClassifications);

        void accountingSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDate(int type);

        void requestAccounting(Activity activity, int id, int typeId, int type, String money, String remark, String time, List<LocalMedia> images);
    }
}
