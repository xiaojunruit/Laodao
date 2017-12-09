package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Fertilizer;
import com.laoodao.smartagri.bean.Seed;
import com.laoodao.smartagri.bean.TruthQuery;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public interface QueryFertilizerContract {
    interface QueryResultView extends ListBaseView {
        void queryFertilizer(List<TruthQuery> data, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestFertilizer(int page, String number, String commonName, String company);
    }
}
