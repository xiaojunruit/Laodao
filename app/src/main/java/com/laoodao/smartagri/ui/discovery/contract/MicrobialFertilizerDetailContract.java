package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.MicrobialFertilizerDetail;


/**
 * Created by Administrator on 2017/5/16.
 */

public interface MicrobialFertilizerDetailContract {


    interface MicrobialFertilizerDetailView extends ListBaseView {
        void getMicrobialFertilizerDetail(MicrobialFertilizerDetail detail);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDetail(int id);
    }
}