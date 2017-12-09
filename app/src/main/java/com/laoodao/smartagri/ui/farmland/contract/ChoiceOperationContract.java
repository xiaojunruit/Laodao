package com.laoodao.smartagri.ui.farmland.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.RecordClassification;

import java.util.List;

/**
 * Created by WORK on 2017/4/25.
 */

public interface ChoiceOperationContract {
    interface ChoiceOperationView extends BaseView {
        void choiceOperationSuccess(List<RecordClassification> list);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestChoiceOperation();
    }
}
