package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Seed;
import com.laoodao.smartagri.bean.TruthQuery;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public interface QuerySeedContract {
    interface QueryResultView extends ListBaseView {
        void querySeed(List<TruthQuery> data, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestQuerySeed(int page, String number, String category, String variety);
    }
}
