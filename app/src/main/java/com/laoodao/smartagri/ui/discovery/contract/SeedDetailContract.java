package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.SeedDetail;


/**
 * Created by Administrator on 2017/5/17.
 */

public interface SeedDetailContract {


    interface SeedDetailView extends ListBaseView {
        void showDetail(SeedDetail detail);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDetail(int id);
    }
}