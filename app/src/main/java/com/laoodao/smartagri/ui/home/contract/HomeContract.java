package com.laoodao.smartagri.ui.home.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.News;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface HomeContract {

    interface HomeView extends ListBaseView {
        void initHome(Home home);

        void initHomeNews(List<News> homeNewsList, boolean isRefresh);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestHomeNews(int page);

        void requestHome(String cityId, String pos);
    }


}
