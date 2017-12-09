package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Discover;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.SupplyMarketing;

import java.util.List;

/**
 * Created by WORK on 2017/4/15.
 */

public interface DiscoverContract {
    interface FindView extends ListBaseView {
        void discoverSuccess(List<NearbyShop> list, boolean isRefresh);

        void menuSucess(List<Discovercat> data);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestDiscover(int page, String lat, String lng);

        void requestMeun(int type);
    }
}
