package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.LoanRecord;

import java.util.List;

/**
 * Created by WORK on 2017/4/14.
 */

public interface LoanRecordListContract {
    interface LoanRecordListView extends ListBaseView{
        void loadRecordSuccess(LoanRecord list);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestLoadRecord();
    }
}
