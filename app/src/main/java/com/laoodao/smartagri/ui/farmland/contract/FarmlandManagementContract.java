package com.laoodao.smartagri.ui.farmland.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.AccountList;
import com.laoodao.smartagri.bean.LedgerStatistics;
import com.laoodao.smartagri.bean.SupplyMarketing;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;

import java.util.List;


/**
 * Created by Administrator on 2017/4/24.
 */

public interface FarmlandManagementContract {
    interface FarmlandManagementView extends ListBaseView {
        void initStatistics(LedgerStatistics statistics);

        void initAccountList(Page<AccountList> result,boolean isRefresh);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestStatistics();

        void requestAccountList(int page);
    }
}