package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.NewsDetail;


/**
 * Created by 欧源 on 2017/4/22.
 */

public interface NewsDetailContract {


    interface NewsDetailView extends ListBaseView {
        void newsDetailSuccess(NewsDetail detail);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestNewsDetail(int id);
        void shareBack(String tag,int id);
    }
}