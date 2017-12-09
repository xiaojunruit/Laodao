package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.PesticideDetail;


/**
 * Created by Administrator on 2017/5/17.
 */

public interface PesticideDetailContract {


    interface PesticideDetailView extends ListBaseView {
        void showDetail(PesticideDetail detail);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDetail(int id);
    }
}