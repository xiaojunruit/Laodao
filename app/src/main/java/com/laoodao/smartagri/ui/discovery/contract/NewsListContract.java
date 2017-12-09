package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.News;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/21.
 */

public interface NewsListContract {


    interface NewsListView extends ListBaseView {
    }

    interface Presenter<T> extends BasePresenter<T> {

        void getNewsList(int page,int gcId,String kw);

    }
}