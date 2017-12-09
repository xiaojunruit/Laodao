package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MicrobialFertilizer;
import com.laoodao.smartagri.bean.TruthQuery;

import java.util.List;


/**
 * Created by Administrator on 2017/5/16.
 */

public interface MicrobialFertilizerContract {


    interface MicrobialFertilizerView extends ListBaseView {
        void queryMicrobialFertilizer(List<TruthQuery> data, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestMicrobialFertilizer(int page, String number, String commonName, String company);
    }
}