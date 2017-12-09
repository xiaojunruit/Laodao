package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.TradeRecord;

import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 */

public interface TradeRecordContract {
    interface TradeRecordView extends ListBaseView {
        void tradeRecordSuccess(List<TradeRecord> list, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestTransactionRecord(int page);
    }
}
