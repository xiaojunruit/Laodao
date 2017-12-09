package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.NewDrug;

import java.util.List;

/**
 * Created by WORK on 2017/5/17.
 */

public interface NewDrugContract {
    interface NewDrugView extends ListBaseView{
    }
    interface Presenter<T> extends BasePresenter<T>{

    }
}
