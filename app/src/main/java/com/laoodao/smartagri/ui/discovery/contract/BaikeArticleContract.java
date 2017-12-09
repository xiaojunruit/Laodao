package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Baike;


/**
 * Created by Administrator on 2017/5/18.
 */

public interface BaikeArticleContract {


    interface BaikeArticleView extends ListBaseView {
        void showBaike(Baike baike);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestBaike(int page, int cropId, int categoryId);

    }
}