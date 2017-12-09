package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.HistoryRecord;

/**
 * Created by WORK on 2017/5/6.
 */

public interface HistoryRecordContract {
    interface HistoryRecordView extends ListBaseView{
        void historyRecordSuccess(HistoryRecord record);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestHistoryRecord(int id);
    }
}
