package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.LoanRecord;
import com.laoodao.smartagri.bean.TradeRecord;

import java.util.List;

/**
 * Created by WORK on 2017/4/19.
 */

public interface TradeRecordDetailContract {
    interface TradeRecordDetailView extends BaseView {
        void tradeRecodeDetailSucess(TradeRecord tradeRecord);

        void tradeCodeSuccess();

        void tradePaySuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestPayment(int id);

        void tradeCode(int id);

        void tradePay(int id, String code);
    }
}
