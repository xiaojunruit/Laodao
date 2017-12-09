package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

/**
 * Created by WORK on 2017/5/19.
 */

public interface ReleaseFormulaContract {
    interface ReleaseFormulaView extends BaseView {
        void releaseFormulaSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestReleaseFormula(String cropName, String disease, String content,int id);
    }
}
